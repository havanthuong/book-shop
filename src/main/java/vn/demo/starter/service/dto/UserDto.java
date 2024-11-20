package vn.demo.starter.service.dto;

import vn.demo.starter.entity.enumeration.UserStatus;

public record UserDto(
        Long id,
        String email,
        String fullName,
        UserStatus status
) {
}
