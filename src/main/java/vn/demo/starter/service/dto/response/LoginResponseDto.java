package vn.demo.starter.service.dto.response;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
) {
}
