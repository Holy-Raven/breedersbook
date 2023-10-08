package ru.codesquad.breed.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.dto.BreedFullDto;
import ru.codesquad.breed.dto.BreedNewDto;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

@Mapper(componentModel = "spring",
        imports = {PetType.class, FurType.class, EnumUtil.class},
        disableSubMappingMethodsGeneration = true)
public interface BreedMapper {

    BreedShortDto returnShortDto(Breed breed);

    BreedFullDto returnFullDto(Breed breed);

    @Mapping(target = "petType", expression = "java(EnumUtil.getValue(PetType.class, dto.getPetType()))")
    @Mapping(target = "furType", expression = "java(EnumUtil.getValue(FurType.class, dto.getFurType()))")
    Breed returnBreed(BreedNewDto dto);
}
