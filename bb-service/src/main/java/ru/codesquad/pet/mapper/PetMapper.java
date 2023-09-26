package ru.codesquad.pet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.codesquad.pet.dto.NewPetDto;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.model.Pet;

@Mapper
public interface PetMapper {
    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);
    @Mapping(target = "gender", expression = "java(Gender.getGenderValue(PetNewDto.getGender()))")
    Pet NewPetDtoToPet(NewPetDto newPetDto);
    PetFullDto PetToPetFullDto(Pet pet);
}
