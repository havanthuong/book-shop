package vn.demo.starter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.demo.starter.entity.User;
import vn.demo.starter.service.dto.UserDto;

public interface UserService {

    User getUser(Long userId);
    UserDto getUserDto(Long userId);
    boolean isActive(Long userId);
    Page<UserDto> getUsers(Pageable pageable);
}
