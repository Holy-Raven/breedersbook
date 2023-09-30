package ru.codesquad.user.dto;

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
public class UserNewDto {

    @Size(min = 4, max = 250, message = "name must be greater than 4 and less than 250")
    @NotBlank(message = "Name cannot be empty and consist only of spaces.")
    String name;

    @Email
    @Size(min = 6, max = 40, message = "Email must be greater than 6 and less than 40")
    @NotBlank(message = "Email cannot be empty and consist only of spaces.")
    String email;

    @Size(min = 4, max = 250, message = "Login must be greater than 4 and less than 250")
    @NotBlank(message = "Login cannot be empty and consist only of spaces.")
    String login;

    @NotBlank(message = "Genger cannot be empty and consist only of spaces.")
    String gender;
}
