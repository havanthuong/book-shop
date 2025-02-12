package vn.demo.starter.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import vn.demo.starter.constant.MessageConstant;

@Data
public class RegisterRequestDto {

    @NotBlank(message = MessageConstant.EMAIL_REQUIRED_ERROR)
    @Length(max = 250, message = MessageConstant.EMAIL_MAX_LENGTH_ERROR)
    @Email(message = MessageConstant.EMAIL_FORMAT_ERROR)
    private String email;

    @NotBlank(message = MessageConstant.PASSWORD_REQUIRED_ERROR)
    @Length(max = 250, message = MessageConstant.PASSWORD_MAX_LENGTH_ERROR)
    @Length(min = 8, message = MessageConstant.PASSWORD_MIN_LENGTH_ERROR)
    private String password;

    @NotBlank(message = MessageConstant.FIRST_NAME_REQUIRED_ERROR)
    @Length(max = 250, message = MessageConstant.FIRST_NAME_MAX_LENGTH_ERROR)
    private String fullName;

    private String phone;

    private MultipartFile photo;
}
