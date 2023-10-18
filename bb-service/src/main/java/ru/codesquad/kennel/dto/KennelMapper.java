package ru.codesquad.kennel.dto;

import ru.codesquad.kennel.Kennel;

import static ru.codesquad.location.LocationMapper.returnLocation;
import static ru.codesquad.location.LocationMapper.returnLocationDto;

public class KennelMapper {
    public static Kennel returnKennel(KennelDto kennelDto) {
        Kennel kennel = Kennel.builder()
                .id(kennelDto.getId())
                .name(kennelDto.getName())
                .descriptions(kennelDto.getDescriptions())
                .phone(kennelDto.getPhone())
                .created(kennelDto.getCreated())
                .photo(kennelDto.getPhoto())
                .build();

        if (kennelDto.getLocation() != null) {
            kennel.setLocation(returnLocation(kennelDto.getLocation()));
        }

        return kennel;
    }

    public static Kennel returnKennel(KennelNewDto kennelNewDto) {
        return Kennel.builder()
                .name(kennelNewDto.getName())
                .descriptions(kennelNewDto.getDescriptions())
                .photo(kennelNewDto.getPhoto())
                .phone(kennelNewDto.getPhone())
                .created(kennelNewDto.getCreated())
                .build();
    }

    public static KennelDto returnKennelDto(Kennel kennel) {
        KennelDto kennelDto = KennelDto.builder()
                .id(kennel.getId())
                .name(kennel.getName())
                .descriptions(kennel.getDescriptions())
                .phone(kennel.getPhone())
                .created(kennel.getCreated())
                .photo(kennel.getPhoto())
                .build();

        if (kennel.getLocation() != null) {
            kennelDto.setLocation(returnLocationDto(kennel.getLocation()));
        }

        return kennelDto;
    }

    public static KennelShortDto returnKennelShortDto(Kennel kennel) {
        KennelShortDto kennelShortDto = KennelShortDto.builder()
                .name(kennel.getName())
                .descriptions(kennel.getDescriptions())
                .phone(kennel.getPhone())
                .photo(kennel.getPhoto())
                .build();

        if (kennel.getLocation() != null) {
            kennelShortDto.setLocation(returnLocationDto(kennel.getLocation()));
        }

        return kennelShortDto;
    }
}
