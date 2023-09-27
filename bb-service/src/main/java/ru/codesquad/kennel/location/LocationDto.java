package ru.codesquad.kennel.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {


    @Size(min = 2, max = 50, message = "country must be greater than 2 and less than 50")
    @NotBlank(message = "country cannot be empty and consist only of spaces.")
    String country;

    @Size(min = 2, max = 50, message = "city must be greater than 2 and less than 50")
    @NotBlank(message = "city cannot be empty and consist only of spaces.")
    String city;

    @Size(min = 2, max = 50, message = "street must be greater than 2 and less than 50")
    @NotBlank(message = "street cannot be empty and consist only of spaces.")
    String street;

    @Size(min = 1, max = 10, message = "house must be greater than 1 and less than 00")
    @NotBlank(message = "house cannot be empty and consist only of spaces.")
    String house;

    @Positive
    Integer apartment;
}
