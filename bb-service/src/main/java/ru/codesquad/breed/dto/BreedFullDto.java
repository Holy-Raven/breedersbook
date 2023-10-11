package ru.codesquad.breed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.util.enums.PetType;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Порода")
public class BreedFullDto {
    long id;
    @Schema(description = "Тип животного: cat / dog", example = "cat")
    PetType petType;
    String name;
    String description;
    @Schema(description = "Длина шерсти: short, long, hairless, curly")
    FurType furType;
    String photoUrl;
}
