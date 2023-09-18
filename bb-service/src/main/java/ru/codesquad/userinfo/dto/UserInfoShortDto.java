package ru.codesquad.userinfo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserInfoShortDto {

    String description;

    String address;

    String phone;

    String photo;

    LocalDateTime birthDate;
}
