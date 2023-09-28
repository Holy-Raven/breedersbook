package ru.codesquad.breed.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.breed.dto.NewBreedDto;

@Mapper(componentModel = "spring",
        disableSubMappingMethodsGeneration = true)
public interface BreedMapper {
    BreedMapper INSTANCE = Mappers.getMapper(BreedMapper.class);

    BreedShortDto toShortDto(Breed breed);

    Breed toBreed(NewBreedDto dto);
}
