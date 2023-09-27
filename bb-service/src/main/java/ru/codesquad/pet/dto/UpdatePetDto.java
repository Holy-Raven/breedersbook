package ru.codesquad.pet.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePetDto {
    @Size(min = 3, max = 3)
    String petType;
    @Size(min = 4, max = 6)
    String gender;
    @Size(min = 1, max = 24)
    String pattern;
    final List<String> colors = new ArrayList<>();
    @Size(min = 1, max = 2000)
    String temper;
    @Size(min = 1, max = 5000)
    String description;
    @Size(min = 1, max = 250)
    String name;
    @PastOrPresent
    LocalDate birthDate;
    @PositiveOrZero
    Integer price;
    Boolean forSale;
    String passportImg;
    Boolean sterilization;
    Long breedId;
}
