package vn.demo.starter.config.audit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private static final String ANONYMOUS = "ANONYMOUS";
    private static final String SYSTEM = "SYSTEM";

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {
            return Optional.of(SYSTEM);
        }

        String userId = authentication.getPrincipal().toString();

        return StringUtils.isBlank(userId)
                ? Optional.of(ANONYMOUS)
                : Optional.of(userId);
    }
}
