package vn.demo.starter.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final SecurityProperties security;
    private final CorsConfiguration cors;
    private final RegisterEmailProperties registerEmail;
    private final CloudinaryProperties cloudinary;

    public record SecurityProperties(
            JwtProperties jwt
    ) {}

    public record JwtProperties(
            String secret,
            long accessTokenInMinutes,
            long refreshTokenInHours
    ) {}

    public record RegisterEmailProperties(
            String redirectUrl,
            long expiredHour,
            long minTimeResend
    ){}

    public record CloudinaryProperties(
            String cloudName,
            String apiKey,
            String apiSecret,
            String folderName
    ){}

}
