package ru.codesquad.kennel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.codesquad.util.Constant.DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Данные для добавления питомника")
public class KennelNewDto {

    @NotBlank(message = "Pet Type can't be blank")
    @Size(min = 3, max = 3, message = "Pet Type size should be exact 3-chars-length")
    String petType;

    @Size(min = 4, max = 250, message = "name must be greater than 4 and less than 250")
    @NotBlank(message = "Name cannot be empty and consist only of spaces.")
    String name;

    @Size(max = 5000, message = "descriptions must less than 250")
    @NotBlank(message = "descriptions cannot be empty and consist only of spaces.")
    String descriptions;

    @Size(min = 5, max = 20, message = "phone must be greater than 5 and less than 20")
    @NotBlank(message = "phone cannot be empty and consist only of spaces.")
    String phone;

    @Size(max = 2048, message = "photo must be less than 2048")
    @NotBlank(message = "photo cannot be empty and consist only of spaces.")
    String photo;

    @NotNull(message = "end cannot be empty.")
    @Past(message = "end may be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    LocalDateTime created;

    Long breedId;
}
