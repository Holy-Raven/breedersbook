package ru.codesquad.kennel.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.kennel.location.LocationDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KennelShortDto {

    String name;

    String descriptions;

    String phone;

    String photo;

    LocationDto location;
}
