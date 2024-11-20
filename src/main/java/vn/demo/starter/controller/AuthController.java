package vn.demo.starter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.demo.starter.security.SecurityUtils;
import vn.demo.starter.service.UploadFileService;
import vn.demo.starter.service.UserService;
import vn.demo.starter.service.dto.UserDto;
import vn.demo.starter.service.dto.request.ChangePasswordRequestDto;
import vn.demo.starter.service.dto.request.LoginRequestDto;
import vn.demo.starter.service.dto.request.RegisterRequestDto;
import vn.demo.starter.service.dto.response.LoginResponseDto;
import vn.demo.starter.service.AuthService;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UploadFileService uploadFileService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDto> loginPortal(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Void> registerPortal(@Valid @RequestBody RegisterRequestDto request) {
        authService.register(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<Void> changePasswordPortal(@Valid @RequestBody ChangePasswordRequestDto request){
        authService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserDto> getUserInfo() {
        return ResponseEntity.ok(userService.getUserDto(SecurityUtils.getCurrentUserId()));
    }
}
