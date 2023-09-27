package ru.codesquad.surgery.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.surgery.enums.SurgeryType;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurgeryPetShortDto {
    SurgeryType type;
    LocalDate date;
}
