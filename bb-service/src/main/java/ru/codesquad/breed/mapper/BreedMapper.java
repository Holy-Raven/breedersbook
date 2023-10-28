package ru.codesquad.breed.mapper;

import ru.codesquad.breed.Breed;
import ru.codesquad.breed.dto.BreedFullDto;
import ru.codesquad.breed.dto.BreedNewDto;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

public class BreedMapper {
    public static BreedShortDto returnShortDto(Breed breed) {
        return BreedShortDto.builder()
                .id(breed.getId())
                .name(breed.getName())
                .build();
    }

    public static BreedFullDto returnFullDto(Breed breed) {
        return BreedFullDto.builder()
                .id(breed.getId())
                .petType(breed.getPetType())
                .name(breed.getName())
                .description(breed.getDescription())
                .furType(breed.getFurType())
                .photoUrl(breed.getPhotoUrl())
                .build();
    }

    public static Breed returnBreed(BreedNewDto breedNewDto) {
        return Breed.builder()
                .petType(EnumUtil.getValue(PetType.class, breedNewDto.getPetType()))
                .name(breedNewDto.getName())
                .description(breedNewDto.getDescription())
                .furType(EnumUtil.getValue(FurType.class, breedNewDto.getFurType()))
                .photoUrl(breedNewDto.getPhotoUrl())
                .build();
    }

    public static Breed returnBreed(BreedFullDto breedFullDtoDto) {
        return Breed.builder()
                .id(breedFullDtoDto.getId())
                .petType(breedFullDtoDto.getPetType())
                .name(breedFullDtoDto.getName())
                .description(breedFullDtoDto.getDescription())
                .furType(breedFullDtoDto.getFurType())
                .photoUrl(breedFullDtoDto.getPhotoUrl())
                .build();
    }
}
