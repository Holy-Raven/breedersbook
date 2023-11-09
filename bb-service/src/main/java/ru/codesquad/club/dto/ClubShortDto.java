package ru.codesquad.club.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.util.enums.PetType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Клуб (краткая информация)")
public class ClubShortDto {

    PetType petType;

    String name;

    String descriptions;

    String photo;

    BreedShortDto breed;
}
