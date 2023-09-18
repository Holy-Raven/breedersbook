package ru.codesquad.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.enums.Gender;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Long id;

    String name;

    String email;

    String login;

    Gender gender;

    UserInfoDto userInfo;

    KennelDto kennel;
}
