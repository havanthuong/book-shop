package vn.demo.starter.security.jwt;

import io.jsonwebtoken.Claims;

public record ExtractRegisterTokenResult(Claims claims ) {
    public Long getUserId() {
        return claims.get("userId", Long.class);
    }

    public String getEmail() {
        return claims.getSubject();
    }
}
