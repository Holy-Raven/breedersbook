package ru.codesquad.breed.mapper;

import org.mapstruct.Mapper;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.breed.dto.NewBreedDto;

@Mapper(componentModel = "spring",
        disableSubMappingMethodsGeneration = true)
public interface BreedMapper {

    BreedShortDto toShortDto(Breed breed);

    Breed toBreed(NewBreedDto dto);
}
