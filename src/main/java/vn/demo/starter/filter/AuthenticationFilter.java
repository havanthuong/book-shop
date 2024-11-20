package vn.demo.starter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.demo.starter.constant.HeaderConstants;
import vn.demo.starter.security.jwt.CheckJwtResult;
import vn.demo.starter.security.jwt.ExtractJwtResult;
import vn.demo.starter.security.jwt.JwtProvider;
import vn.demo.starter.service.UserService;
import vn.demo.starter.service.UserSessionService;

import java.io.IOException;
import java.util.Set;


@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserSessionService sessionService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String accessToken = retrieveToken(request);

        if (StringUtils.isNotBlank(accessToken)) {
            ExtractJwtResult extractJwtResult = jwtProvider.extractClaims(accessToken);
            if (extractJwtResult.isValid() && sessionService.checkUserSession(extractJwtResult.getTokenId())
                    && userService.isActive(extractJwtResult.getUserId())) {
                Set<String> author = extractJwtResult
                        .getAuthorities();
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        extractJwtResult.getUserId(),
                        extractJwtResult.getTokenId(),
                        author.stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                if (CheckJwtResult.EXPIRED == extractJwtResult.status()) {
                    response.setHeader(HeaderConstants.IS_TOKEN_EXPIRED_HEADER, "true");
                }
                setAnonymousContext();
            }
        } else {
            setAnonymousContext();
        }

        filterChain.doFilter(request, response);
    }

    private String retrieveToken(HttpServletRequest request) {
        final String token = request.getHeader(HeaderConstants.AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(token) || !token.startsWith(HeaderConstants.TOKEN_PREFIX)) {
            return StringUtils.EMPTY;
        }
        return token.replace(HeaderConstants.TOKEN_PREFIX, StringUtils.EMPTY).trim();
    }

    private void setAnonymousContext() {
        SecurityContextHolder.clearContext();
//        SecurityContextHolder.getContext().setAuthentication(SecurityUtils.createAnonymous());
    }
}
