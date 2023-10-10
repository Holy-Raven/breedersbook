package ru.codesquad.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.enums.Gender;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Пользователь (краткая информация)")
public class UserShortDto {

    String name;

    Gender gender;

    UserInfoDto userInfo;
}
