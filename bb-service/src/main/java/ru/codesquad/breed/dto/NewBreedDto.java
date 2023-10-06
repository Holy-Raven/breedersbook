package ru.codesquad.breed.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.util.enums.PetType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewBreedDto {
    @NotNull
    PetType petType;
    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotNull
    FurType furType;
    @NotBlank
    String photoUrl;
}
