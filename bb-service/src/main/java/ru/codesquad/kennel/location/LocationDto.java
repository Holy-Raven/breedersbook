package ru.codesquad.kennel.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {

    String country;

    String city;

    String street;

    String house;

    Integer apartment;
}
