package ru.codesquad.breed.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.pet.mapper.PetMapper;

@Mapper
public interface BreedMapper {
    BreedMapper INSTANCE = Mappers.getMapper(BreedMapper.class);

    BreedShortDto toShortDto(Breed breed);
}
