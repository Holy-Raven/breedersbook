package ru.codesquad.showPart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static ru.codesquad.util.Constant.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Данные для добавления участия в выставке")
public class ShowPartNewDto {
    @NotBlank(message = "Show rank can't be blank")
    @Size(min = 1, max = 250, message = "Show rank should be less than 250 symbols")
    @Schema(description = "Ранг выставки", example = "CACIB")
    String showRank;
    @NotBlank(message = "Club name can't be blank")
    @Size(min = 1, max = 250, message = "Club name should be less than 250 symbols")
    @Schema(description = "Название клуба", example = "МКОО \"Норд\"")
    String club;
    @NotBlank(message = "Class can't be blank")
    @Size(min = 1, max = 250, message = "Class should be less than 250 symbols")
    @Schema(description = "Выставочный класс", example = "PUPPY / KITTEN / CHAMPION / VETERAN")
    String ageClass;
    @NotBlank(message = "Mark can't be blank")
    @Size(min = 1, max = 250, message = "Mark should be less than 250 symbols")
    @Schema(description = "Оценка", example = "EXCELLENT / GOOD")
    String mark;
    @Size(min = 1, max = 250, message = "Title should be less than 250 symbols")
    @Schema(description = "Титул, полученный на выставке", example = "САС")
    String title;
    @NotNull
    @PastOrPresent(message = "Date should be in past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Schema(description = "Дата выставки")
    LocalDate date;
    @NotNull
    @Size(min = 1, max = 2048, message = "PhotoUrl  should be less than 2048 symbols")
    @Schema(description = "Фото подтверждающего документа")
    String photoUrl;
}
