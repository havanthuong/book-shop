package vn.demo.starter.security;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vn.demo.starter.constant.AppConstants;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.security.config.Elements.ANONYMOUS;

public final class SecurityUtils {
    private static final String[] HEADERS_IP = {
            "true-client-ip",
            "cf-connecting-ip",
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private SecurityUtils() {}

    public static String generateRandomCode() {
        return UUID.randomUUID().toString().replace("-", AppConstants.EMPTY_STR);
    }

    public static Long getCurrentUserId() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(Object::toString)
                .map(Long::parseLong)
                .orElse(null);
    }

    public static String getCurrentTokenId() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getCredentials)
                .map(Object::toString)
                .orElse(null);
    }

    public static Authentication createAnonymous() {
        return new UsernamePasswordAuthenticationToken(
//                StringUtils.EMPTY,
                "3",
                StringUtils.EMPTY,
                List.of(new SimpleGrantedAuthority(ANONYMOUS))
        );
    }

    public static Pair<String, String> getCurrentUserAgentAndIpAddress() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return Pair.of("N/A", "N/A");
        }
        return Optional.ofNullable(attributes.getRequest())
                .map(request -> Pair.of(request.getHeader("User-Agent"), getClientIpAddress(request)))
                .orElse(Pair.of("N/A", "N/A"));
    }

    private static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_IP) {
            String ip = request.getHeader(header);
            if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
