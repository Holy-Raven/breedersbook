package ru.codesquad.pet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.codesquad.util.Constant.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetNewDto {
    @NotBlank(message = "Pet Type can't be blank")
    @Size(min = 3, max = 3, message = "Pet Type size should be exact 3-chars-length")
    String petType;
    @NotBlank(message = "Gender can't be blank")
    @Size(min = 4, max = 6, message = "")
    String gender;
    @NotBlank(message = "Pattern can't be blank")
    @Size(min = 1, max = 24, message = "")
    String pattern;
    @NotNull(message = "Pet should have at least one color")
    @NotEmpty(message = "Pet should have at least one color")
    List<String> colors = new ArrayList<>();
    @NotBlank(message = "Temper can't be blank")
    @Size(min = 1, max = 2000, message = "")
    String temper;
    @NotBlank(message = "Description can't be blank")
    @Size(min = 1, max = 5000, message = "Description should be less than 5000 symbols")
    String description;
    @NotBlank(message = "Name can't be blank")
    @Size(min = 1, max = 250, message = "Name should be less than 250 symbols")
    String name;
    @PastOrPresent(message = "Birth Date should in past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate birthDate;
    @PositiveOrZero(message = "Price should be >= 0")
    Integer price;
    boolean forSale;
    String passportImg;
    boolean sterilized;
    @NotNull(message = "You should specify breed of your pet")
    Long breedId;
}