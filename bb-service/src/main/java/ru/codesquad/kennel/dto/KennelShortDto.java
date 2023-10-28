package ru.codesquad.kennel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.location.LocationDto;
import ru.codesquad.util.enums.PetType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Питомник (краткая информация)")
public class KennelShortDto {

    PetType petType;

    String name;

    String descriptions;

    String phone;

    String photo;

    LocationDto location;

    BreedShortDto breed;
}
