package vn.demo.starter.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.demo.starter.entity.User;
import vn.demo.starter.service.dto.UserDto;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUserDetail().getFullName(),
                user.getStatus()
        );
    }

}
