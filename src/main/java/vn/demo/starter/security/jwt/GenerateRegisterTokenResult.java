package vn.demo.starter.security.jwt;

import java.time.Instant;

public record GenerateRegisterTokenResult(
        String accessToken,
        Instant expiredDate
) {
}
