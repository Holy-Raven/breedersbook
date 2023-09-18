package ru.codesquad.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.util.enums.Gender;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateDto {

    @NotNull
    Long id;

    String name;

    String email;

    String login;

    Gender gender;
}
