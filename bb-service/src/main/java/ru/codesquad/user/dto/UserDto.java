package ru.codesquad.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.location.LocationDto;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.enums.Gender;
import java.time.LocalDateTime;
import static ru.codesquad.util.Constant.DATE_TIME_FORMAT;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    LocalDateTime created;

    UserInfoDto userInfo;

    LocationDto location;

    KennelDto kennel;
}
