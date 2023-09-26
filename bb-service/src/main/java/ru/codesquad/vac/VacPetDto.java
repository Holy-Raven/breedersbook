package ru.codesquad.vac;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.vac.enums.VacType;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacPetDto {
    String name;
    VacType type;
    LocalDate date;
}