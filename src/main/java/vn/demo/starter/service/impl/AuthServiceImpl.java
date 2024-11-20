package vn.demo.starter.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.demo.starter.constant.AppConstants;
import vn.demo.starter.constant.MessageConstant;
import vn.demo.starter.entity.User;
import vn.demo.starter.exception.BadRequestException;
import vn.demo.starter.security.SecurityUtils;
import vn.demo.starter.security.jwt.GenerateJwtResult;
import vn.demo.starter.security.jwt.JwtProvider;
import vn.demo.starter.service.dto.request.ChangePasswordRequestDto;
import vn.demo.starter.service.dto.request.LoginRequestDto;
import vn.demo.starter.service.dto.request.RegisterRequestDto;
import vn.demo.starter.entity.UserDetail;
import vn.demo.starter.entity.UserSession;
import vn.demo.starter.entity.enumeration.Role;
import vn.demo.starter.entity.enumeration.UserStatus;
import vn.demo.starter.exception.AuthenticationException;
import vn.demo.starter.repository.UserRepository;
import vn.demo.starter.service.AuthService;
import vn.demo.starter.service.UserSessionService;
import vn.demo.starter.service.dto.response.LoginResponseDto;
import vn.demo.starter.util.DateUtils;

import java.time.Instant;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final UserSessionService userSessionService;

    private final JwtProvider jwtProvider;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(RegisterRequestDto request) {
        log.info("Start create the new user with email: {}", request.getEmail());

        userRepository
                .findByEmail(request.getEmail())
                .ifPresent(user -> {
                    log.error("The user email already exist!");
                    throw new BadRequestException(MessageConstant.USER_ALREADY_EXIST_ERR);
                });

        User user = userRepository.save(User
                .builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .phone(request.getPhone())
                .status(UserStatus.INACTIVE)
                .build());

        user.setUserDetail(UserDetail.builder()
                .fullName(Objects.nonNull(user.getUserDetail()) ? request.getFullName() : null)
                .user(user)
                .build());

        log.info("Create new user done, new user id: {}", user.getId());
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException(MessageConstant.INVALID_CREDENTIAL_ERR));

        if (UserStatus.BLOCKED_BY_ADMIN.equals(user.getStatus())) {
            throw new AuthenticationException(MessageConstant.USER_WAS_BLOCKED_ERR);
        }

        if (user.inBlockedTime()) {
            throw new AuthenticationException(MessageConstant.USER_WAS_BLOCKED_ERR);
        }

        if (UserStatus.INACTIVE.equals(user.getStatus())) {
            throw new AuthenticationException(MessageConstant.USER_WAS_INACTIVE_ERR);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            if (user.getBlockedCount() >= AppConstants.MAX_LOGIN_FAILED && Objects.nonNull(user.getBlockedDate())) {
                log.info("Reset blocked count and blocked date for user: {}", user.getId());
                user.resetBlocked();
            }

            int blockedCount = user.getBlockedCount() + 1;
            Instant blockedDate = blockedCount >= AppConstants.MAX_LOGIN_FAILED ? DateUtils.currentInstant() : user.getBlockedDate();
            UserStatus userStatus = blockedCount >= AppConstants.MAX_LOGIN_FAILED ? UserStatus.BLOCKED : user.getStatus();
            log.info("New blocked count: {}, blocked date: {} of user: {}", blockedCount, blockedDate, user.getId());
            userRepository.forceUpdateBlockedCountAndDate(blockedCount, blockedDate, user.getId(), userStatus);
            throw new AuthenticationException(
                    blockedCount >= AppConstants.MAX_LOGIN_FAILED
                            ? MessageConstant.INVALID_CREDENTIAL_AND_BLOCKED_ERR
                            : MessageConstant.INVALID_CREDENTIAL_ERR
            );
        }
        user.resetBlocked();
        GenerateJwtResult jwtTokens = jwtProvider.generateToken(user);
        user.setNewSession(new UserSession(jwtTokens.tokenId(), jwtTokens.expiredDate()));

        return new LoginResponseDto(jwtTokens.accessToken(), jwtTokens.refreshToken());

    }

    @Override
    @Transactional
    public void logout() {
        String tokenId = SecurityUtils.getCurrentTokenId();
        userSessionService.removeExpiredSession(tokenId);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequestDto request) {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.lockById(userId).orElseThrow(() -> new BadRequestException(MessageConstant.INVALID_CREDENTIAL_ERR));
        log.info("Start change password for user with email: {}", user.getEmail());
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(MessageConstant.INVALID_PASSWORD_ERR);
        }
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BadRequestException(MessageConstant.SAME_PASSWORD_ERR);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("Change password for user {} done", user.getId());
    }
}
