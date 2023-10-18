package ru.codesquad.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Данные для добавления нового пользователя")
public class UserNewDto {

    @Size(max = 250, message = "firstName must be less than 250")
    @NotBlank(message = "firstName cannot be empty and consist only of spaces.")
    String firstName;

    @Size(max = 250, message = "lastName must be less than 250")
    @NotBlank(message = "lastName cannot be empty and consist only of spaces.")
    String lastName;

    @Email
    @Size(min = 6, max = 40, message = "Email must be greater than 6 and less than 40")
    @NotBlank(message = "Email cannot be empty and consist only of spaces.")
    String email;

    @Size(min = 4, max = 250, message = "username must be greater than 4 and less than 250")
    @NotBlank(message = "username cannot be empty and consist only of spaces.")
    String username;

    @Size(min = 8, max = 250, message = "password must be greater than 4 and less than 250")
    @NotBlank(message = "password cannot be empty and consist only of spaces.")
    String password;
}
