package vn.demo.starter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.demo.starter.constant.MessageConstant;
import vn.demo.starter.entity.User;
import vn.demo.starter.exception.BadRequestException;
import vn.demo.starter.service.UserService;
import vn.demo.starter.repository.UserRepository;
import vn.demo.starter.service.dto.UserDto;
import vn.demo.starter.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public User getUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException(MessageConstant.USER_NOT_EXIST));
    }

    @Override
    public UserDto getUserDto(Long userId) {
        User user = getUser(userId);
        return userMapper.toDto(user);
    }

    @Override
    public boolean isActive(Long userId) {
        return this.getUser(userId).isActive();
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }
}
