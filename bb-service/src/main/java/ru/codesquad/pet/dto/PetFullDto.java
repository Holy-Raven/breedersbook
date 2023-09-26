package ru.codesquad.pet.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.award.AwardPetDto;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.disease.DiseasePetDto;
import ru.codesquad.kennel.dto.KennelShortDto;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.util.enums.PetType;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.surgery.SurgeryPetDto;
import ru.codesquad.user.dto.UserShortDto;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.vac.VacPetDto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetFullDto {
    long id;
    PetType petType;
    Gender gender;
    String pattern;
    Set<Color> colors = new HashSet<>();
    String temper;
    String description;
    String name;
    LocalDate birthDate;
    int price;
    SaleStatus saleStatus;
    String passportImg;
    boolean sterilization;
    BreedShortDto breed;
    UserShortDto owner;
    KennelShortDto kennel;

    Set<VacPetDto> vacs = new HashSet<>();
    Set<AwardPetDto> awards = new HashSet<>();
    Set<DiseasePetDto> diseases = new HashSet<>();
    Set<SurgeryPetDto> surgeries = new HashSet<>();
}