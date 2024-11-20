package vn.demo.starter.security.jwt;

import java.time.Instant;

public record GenerateJwtResult(
        String tokenId,
        String accessToken,
        String refreshToken,
        Instant expiredDate
) {
}
