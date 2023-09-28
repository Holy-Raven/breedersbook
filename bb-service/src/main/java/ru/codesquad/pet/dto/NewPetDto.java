package ru.codesquad.pet.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewPetDto {
    @NotBlank
    @Size(min = 3, max = 3)
    String petType;
    @NotBlank
    @Size(min = 4, max = 6)
    String gender;
    @NotBlank
    @Size(min = 1, max = 24)
    String pattern;
    @NotNull
    List<String> colors = new ArrayList<>();
    @NotBlank
    @Size(min = 1, max = 2000)
    String temper;
    @NotBlank
    @Size(min = 1, max = 5000)
    String description;
    @NotBlank
    @Size(min = 1, max = 250)
    String name;
    @PastOrPresent
    LocalDate birthDate;
    @PositiveOrZero
    int price;
    boolean forSale;
    String passportImg;
    boolean sterilized;
    @NotNull
    Long breedId;
}