package ru.codesquad.kennel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.codesquad.util.Constant.DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Данные для обновления питомника")
public class KennelUpdateDto {

    @Size(min = 3, max = 3, message = "Pet Type size should be exact 3-chars-length")
    String petType;

    @Size(min = 4, max = 250, message = "name must be greater than 4 and less than 250")
    String name;

    @Size(max = 5000, message = "descriptions must less than 250")
    String descriptions;

    @Size(min = 5, max = 20, message = "phone must be greater than 5 and less than 20")
    String phone;

    @Size(max = 2048, message = "photo must be less than 2048")
    String photo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    LocalDateTime created;

    Long breedId;
}
