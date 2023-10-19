package ru.codesquad.pet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.user.dto.UserShortDto;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Питомец")
public class PetFullDto {
    long id;
    PetType petType;
    Gender gender;
    String pattern;
    List<Color> colors = new ArrayList<>();
    String temper;
    String description;
    String name;
    LocalDate birthDate;
    Integer price;
    SaleStatus saleStatus;
    String passportImg;
    boolean sterilized;
    BreedShortDto breed;
    UserShortDto owner;
    KennelDto kennel;
}