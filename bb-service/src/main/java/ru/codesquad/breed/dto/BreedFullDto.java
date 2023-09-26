package ru.codesquad.breed.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.enums.FurType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BreedFullDto {
    long id;
    String name;
    String description;
    FurType furType;
    String photoUrl;
}
