package ru.codesquad.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.enums.Gender;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShortDto {

    String name;

    Gender gender;

    UserInfoDto userInfoDto;
}
