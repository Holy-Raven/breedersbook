package ru.codesquad.kennel.dto;

import lombok.RequiredArgsConstructor;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.mapper.BreedMapper;
import ru.codesquad.breed.repository.BreedRepository;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.pet.enums.CatPattern;
import ru.codesquad.pet.enums.DogPattern;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

import static ru.codesquad.location.LocationMapper.returnLocation;
import static ru.codesquad.location.LocationMapper.returnLocationDto;

public class KennelMapper {

    public static Kennel returnKennel(KennelDto kennelDto, Breed breed) {
        Kennel kennel = Kennel.builder()
                .id(kennelDto.getId())
                .breed(breed)
                .type(kennelDto.getPetType())
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

    public static Kennel returnKennel(KennelNewDto kennelNewDto, Breed breed) {
        PetType type = EnumUtil.getValue(PetType.class, kennelNewDto.getPetType());
        return Kennel.builder()
                .type(type)
                .breed(breed)
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
                .petType(kennel.getType())
                .name(kennel.getName())
                .descriptions(kennel.getDescriptions())
                .phone(kennel.getPhone())
                .created(kennel.getCreated())
                .photo(kennel.getPhoto())
                .build();

        if (kennel.getLocation() != null) {
            kennelDto.setLocation(returnLocationDto(kennel.getLocation()));
        }
        if (kennel.getBreed() != null) {
            kennelDto.setBreed(BreedMapper.returnShortDto(kennel.getBreed()));
        }
        return kennelDto;
    }

    public static KennelShortDto returnKennelShortDto(Kennel kennel) {
        KennelShortDto kennelShortDto = KennelShortDto.builder()
                .petType(kennel.getType())
                .name(kennel.getName())
                .descriptions(kennel.getDescriptions())
                .phone(kennel.getPhone())
                .photo(kennel.getPhoto())
                .build();

        if (kennel.getLocation() != null) {
            kennelShortDto.setLocation(returnLocationDto(kennel.getLocation()));
        }
        if (kennel.getBreed() != null) {
            kennelShortDto.setBreed(BreedMapper.returnShortDto(kennel.getBreed()));
        }
        return kennelShortDto;
    }
}
