package ru.codesquad.pet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static ru.codesquad.util.Constant.DATE_FORMAT;
import static ru.codesquad.util.Constant.DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetUpdateDto {
    @Size(min = 3, max = 3, message = "Pet Type size should be exact 3-chars-length")
    String petType;
    @Size(min = 4, max = 6, message = "Gender can be only MALE OR FEMALE")
    String gender;
    @Size(min = 1, max = 24, message = "Pattern length should be less than 24")
    String pattern;
    List<String> colors;
    @Size(min = 1, max = 2000, message = "Temper description length should be less than 2000")
    String temper;
    @Size(min = 1, max = 5000, message = "Description length should be less than 2000")
    String description;
    @Size(min = 1, max = 250, message = "Name length should be less than 250")
    String name;
    @PastOrPresent(message = "Birth Date should in past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate birthDate;
    @PastOrPresent(message = "You may have reasons, but Death Date should in past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDate deathDate;
    @PositiveOrZero(message = "Price should be >= 0")
    Integer price;
    String saleStatus;
    String passportImg;
    Boolean sterilized;
    Long breedId;
}
