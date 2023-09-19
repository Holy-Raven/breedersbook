package ru.codesquad.userinfo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoUpdateDto {

    @NotNull
    Long id;

    String description;

    String address;

    String phone;

    String photo;

    LocalDateTime birthDate;
}
