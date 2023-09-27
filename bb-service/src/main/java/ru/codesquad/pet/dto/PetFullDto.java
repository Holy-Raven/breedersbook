package ru.codesquad.pet.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.award.dto.AwardPetShortDto;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.disease.dto.DiseasePetDto;
import ru.codesquad.kennel.dto.KennelShortDto;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.surgery.dto.SurgeryPetShortDto;
import ru.codesquad.user.dto.UserShortDto;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;
import ru.codesquad.vac.VacPetShortDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    final List<Color> colors = new ArrayList<>();
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

    final List<VacPetShortDto> vacs = new ArrayList<>();
    final List<AwardPetShortDto> awards = new ArrayList<>();
    final List<DiseasePetDto> diseases = new ArrayList<>();
    final List<SurgeryPetShortDto> surgeries = new ArrayList<>();
}