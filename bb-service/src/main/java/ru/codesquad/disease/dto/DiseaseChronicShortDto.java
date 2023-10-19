package ru.codesquad.disease.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Заболевание (краткая информация о хроническом заболевании)")
public class DiseaseChronicShortDto {
    long id;
    String name;
}
