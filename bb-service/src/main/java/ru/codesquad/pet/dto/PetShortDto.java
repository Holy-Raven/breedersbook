package ru.codesquad.pet.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.kennel.dto.KennelShortDto;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetShortDto {
    long id;
    PetType petType;
    Gender gender;
    String pattern;
    List<Color> colors = new ArrayList<>();
    String name;
    String description;
    LocalDate birthDate;
    int price;
    BreedShortDto breed;
    KennelShortDto kennel;
}
