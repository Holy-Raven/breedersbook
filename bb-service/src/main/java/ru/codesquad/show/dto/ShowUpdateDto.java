package ru.codesquad.show.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class ShowUpdateDto {
    @Size(min = 1, max = 250, message = "Show rank should be less than 250 symbols")
    String showRank;
    @Size(min = 1, max = 250, message = "Club name should be less than 250 symbols")
    String club;
    @Size(min = 1, max = 250, message = "Age class should be less than 250 symbols")
    String ageClass;
    @Size(min = 1, max = 250, message = "Mark should be less than 250 symbols")
    String mark;
    @Size(min = 1, max = 250, message = "Title should be less than 250 symbols")
    String title;
    @PastOrPresent(message = "Date should be in past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate date;
    @Size(min = 1, max = 2048, message = "PhotoUrl  should be less than 2048 symbols")
    String photoUrl;
}