package ru.codesquad.userinfo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import static ru.codesquad.util.Constant.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoUpdateDto {

    @Size(max = 5000, message = "description must be less than 5000")
    String description;

    @Size(min = 5, max = 20, message = "phone must be greater than 5 and less than 20")
    String phone;

    @Size(max = 250, message = "photo must be less than 250")
    String photo;

    @Past(message = "end may be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDateTime birthDate;
}
