package vn.demo.starter.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import vn.demo.starter.constant.MessageConstant;

@Data
public class LoginRequestDto {

    @NotBlank(message = MessageConstant.EMAIL_REQUIRED_ERROR)
    @Email(message = MessageConstant.EMAIL_FORMAT_ERROR)
    private String email;

    @NotBlank(message = MessageConstant.PASSWORD_REQUIRED_ERROR)
    private String password;

}