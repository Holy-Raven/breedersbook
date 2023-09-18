package ru.codesquad.kennel.dto;

import lombok.experimental.UtilityClass;
import ru.codesquad.kennel.Kennel;

@UtilityClass
public class MapperKennel {

    public KennelDto returnKennelDto(Kennel kennel) {
        KennelDto kennelDto = KennelDto.builder()
                .build();
        return kennelDto;
    }

    public static Kennel returnKennel(KennelDto kennelDto) {
        Kennel kennel = Kennel.builder()
                .build();
        return kennel;
    }
}
