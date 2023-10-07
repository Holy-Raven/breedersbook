package ru.codesquad.breed.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.util.enums.PetType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BreedNewDto {
    @NotBlank(message = "Pet Type can't be blank")
    @Size(min = 3, max = 3, message = "Pet Type size should be exact 3-chars-length")
    String petType;
    @NotBlank(message = "Name can't be blank")
    @Size(min = 1, max = 250, message = "Name should be less than 250 symbols")
    String name;
    @NotBlank(message = "Description can't be blank")
    @Size(min = 1, max = 5000, message = "Description should be less than 5000 symbols")
    String description;
    @NotBlank(message = "Fur Type can't be blank")
    @Size(min = 1, max = 8, message = "Fur type should be less than 8 symbols")
    String furType;
    String photoUrl;
}
