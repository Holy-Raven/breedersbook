package ru.codesquad.kennel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.codesquad.kennel.location.LocationDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Питомник (краткая информация)")
public class KennelShortDto {

    String name;

    String descriptions;

    String phone;

    String photo;

    LocationDto location;
}
