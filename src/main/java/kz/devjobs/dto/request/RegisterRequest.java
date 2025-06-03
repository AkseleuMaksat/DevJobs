package kz.devjobs.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kz.devjobs.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 6, max = 30, message = "Пароль должен содержать от 6 до 30 символов")
    private String password;

    @NotNull(message = "Роль не должна быть null")
    private Role role;
}
