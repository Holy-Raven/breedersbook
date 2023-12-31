package ru.codesquad.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.location.LocationDto;
import ru.codesquad.role.Role;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.enums.Status;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.codesquad.util.Constant.DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Пользователь")
public class UserDto {

    Long id;

    String firstName;

    String lastName;

    String email;

    String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    LocalDateTime created;

    UserInfoDto userInfo;

    LocationDto location;

    KennelDto kennel;

    Status status;

    Collection<Role> roles;
}
