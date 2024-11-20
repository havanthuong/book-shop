package vn.demo.starter.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.demo.starter.constant.AppConstants;
import vn.demo.starter.entity.User;
import vn.demo.starter.config.ApplicationProperties;
import vn.demo.starter.security.SecurityUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
public class JwtProvider {

    private final Key signingKey;
    private final long accessTokenInMinutes;
    private final long refreshTokenInHours;
    private final long expiredRegisterTokenHours;

    public JwtProvider(ApplicationProperties properties) {
        this.signingKey = getSignInKey(properties.getSecurity().jwt().secret());
        this.accessTokenInMinutes = properties.getSecurity().jwt().accessTokenInMinutes();
        this.refreshTokenInHours = properties.getSecurity().jwt().refreshTokenInHours();
        this.expiredRegisterTokenHours = properties.getRegisterEmail().expiredHour();
    }

    public GenerateJwtResult generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public GenerateJwtResult generateToken(Map<String, Object> extraClaims, User user) {
        String refreshTokenId = SecurityUtils.generateRandomCode();
        String accessTokenId = SecurityUtils.generateRandomCode();
        Date issueAt = new Date(System.currentTimeMillis());

        Set<String> authorities = Set.of(user.getRole().toString());

        String refreshToken = Jwts.builder()
                .setId(refreshTokenId)
                .claim("userId", user.getId())
                .setSubject(user.getEmail())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .setIssuedAt(issueAt)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * refreshTokenInHours))
                .compact();

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * accessTokenInMinutes);

        String accessToken = Jwts.builder()
                .setClaims(extraClaims)
                .setId(accessTokenId)
                .claim("userId", user.getId())
                .claim("fullName", Objects.nonNull(user.getUserDetail()) ? user.getUserDetail().getFullName() : AppConstants.EMPTY_STR)
                .setSubject(user.getEmail())
                .claim("refreshId", refreshTokenId)
                .claim("authorities", authorities)
                .setIssuedAt(issueAt)
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        return new GenerateJwtResult(
                accessTokenId,
                accessToken,
                refreshToken,
                expiration.toInstant()
        );
    }

    public ExtractJwtResult extractClaims(String token) {
        try {
            return new ExtractJwtResult(CheckJwtResult.VALID, extractAllClaims(token));
        } catch (ExpiredJwtException exception) {
            return new ExtractJwtResult(CheckJwtResult.EXPIRED, exception.getClaims());
        } catch (Exception exception) {
            return new ExtractJwtResult(CheckJwtResult.INVALID, null);
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(String secret) {
        final byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public GenerateRegisterTokenResult generateRegisterToken(User user){
        Date issueAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiredRegisterTokenHours);
        Map<String, Object> extraClaims = new HashMap<>();

        String accessToken = Jwts.builder()
                .setClaims(extraClaims)
                .claim("userId", user.getId())
                .setSubject(user.getEmail())
                .setIssuedAt(issueAt)
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        return new GenerateRegisterTokenResult(
                accessToken,
                expiration.toInstant()
        );
    }

    public ExtractRegisterTokenResult extractRegisterTokenResult(String token){
        return new ExtractRegisterTokenResult(extractAllClaims(token));
    }
}
