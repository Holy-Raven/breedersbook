package ru.codesquad.security;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequest {

    @Size(min = 4, max = 250, message = "username must be greater than 4 and less than 250")
    @NotBlank(message = "username cannot be empty and consist only of spaces.")
    String username;

    @Size(min = 8, max = 250, message = "password must be greater than 4 and less than 250")
    @NotBlank(message = "password cannot be empty and consist only of spaces.")
    String password;
}
