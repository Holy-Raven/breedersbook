package ru.codesquad.vac.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Обработка: вакцинация, от блох / клещей, глистование (краткая информация)")
public class VacShortDto {
    long id;
    String type;
    LocalDate date;
}
