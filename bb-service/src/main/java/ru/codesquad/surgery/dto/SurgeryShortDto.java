package ru.codesquad.surgery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Операция (краткая информация)")
public class SurgeryShortDto {
    long id;
    String name;
    String type;
    LocalDate date;
}
