package ru.codesquad.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Данные для обновления пользователя")
public class UserUpdateDto {

    @Size(min = 4, max = 250, message = "name must be greater than 4 and less than 250")
    String name;

    @Email
    @Size(min = 6, max = 40, message = "Email must be greater than 6 and less than 40")
    String email;

    @Size(min = 4, max = 250, message = "Login must be greater than 4 and less than 250")
    String login;

    String gender;
}
