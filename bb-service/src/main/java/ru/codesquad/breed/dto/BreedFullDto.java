package ru.codesquad.breed.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.util.enums.PetType;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BreedFullDto {
    long id;
    PetType petType;
    String name;
    String description;
    FurType furType;
    String photoUrl;
}
