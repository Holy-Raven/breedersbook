package ru.codesquad.breed.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.dto.BreedFullDto;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.breed.dto.BreedNewDto;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring",
        imports = {PetType.class, FurType.class, EnumUtil.class},
        disableSubMappingMethodsGeneration = true)
public interface BreedMapper {

    BreedShortDto toShortDto(Breed breed);

    BreedFullDto toFullDto(Breed breed);

    @Mapping(target = "petType", expression = "java(EnumUtil.getValue(PetType.class, dto.getPetType()))")
    @Mapping(target = "furType", expression = "java(EnumUtil.getValue(FurType.class, dto.getFurType()))")
    Breed toBreed(BreedNewDto dto);
}
