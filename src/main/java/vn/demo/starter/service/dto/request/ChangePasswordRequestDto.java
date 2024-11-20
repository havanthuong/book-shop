package vn.demo.starter.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import vn.demo.starter.constant.MessageConstant;

@Data
public class ChangePasswordRequestDto {

    @NotBlank(message = MessageConstant.PASSWORD_REQUIRED_ERROR)
    private String oldPassword;

    @NotBlank(message = MessageConstant.PASSWORD_REQUIRED_ERROR)
    @Length(max = 250, message = MessageConstant.PASSWORD_MAX_LENGTH_ERROR)
    @Length(min = 8, message = MessageConstant.PASSWORD_MIN_LENGTH_ERROR)
    private String newPassword;
    
}
