package ru.codesquad.userinfo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.user.dto.UserShortInfoDto;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoDto {

    Long id;

    String description;

    String address;

    String phone;

    String photo;

    LocalDateTime birthDate;

    UserShortInfoDto owner;
}
