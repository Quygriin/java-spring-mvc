package vn.hoidanit.laptopshop.service.validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO; // Import class DTO chứa dữ liệu đăng ký
import vn.hoidanit.laptopshop.service.UserService;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {
    public final UserService userService;

    public RegisterValidator(UserService userService){
        this.userService=userService;
    }

    @Override
    public void initialize(RegisterChecked constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegisterDTO registerDTO, ConstraintValidatorContext context) {
        if (registerDTO == null) {
            return false;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp nhau không
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords nhập lại sai")
                   .addPropertyNode("confirmPassword")
                   .addConstraintViolation();
            return false;
        }
     if(this.userService.checkEmailExists(registerDTO.getEmail())){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Email đã tồn tại")
               .addPropertyNode("email")
               .addConstraintViolation();
        return false;
     }
        return true;
    }
}
