package vn.demo.starter.service;

import vn.demo.starter.service.dto.request.ChangePasswordRequestDto;
import vn.demo.starter.service.dto.request.LoginRequestDto;
import vn.demo.starter.service.dto.request.RegisterRequestDto;
import vn.demo.starter.service.dto.response.LoginResponseDto;

public interface AuthService {

    void register(RegisterRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
    void logout();
    void changePassword(ChangePasswordRequestDto request);
}
