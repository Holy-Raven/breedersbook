package ru.codesquad.userinfo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoNewDto {

    @Size(max = 5000, message = "description must be less than 5000")
    String description;

    @Size(max = 1000, message = "address must be less than 1000")
    String address;

    @Size(min = 5, max = 20, message = "phone must be greater than 5 and less than 20")
    String phone;

    @Size(max = 250, message = "photo must be less than 250")
    String photo;

    @NotNull(message = "end cannot be empty.")
    @Past(message = "end may be in the past")
    LocalDateTime birthDate;
}
