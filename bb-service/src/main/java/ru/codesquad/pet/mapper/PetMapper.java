package ru.codesquad.pet.mapper;

import ru.codesquad.breed.Breed;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetNewDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.user.User;

import java.util.List;


public interface PetMapper {
    Pet toPet(PetNewDto petNewDto, User owner, Breed breed);

    PetFullDto toFullDto(Pet pet);

    PetShortDto toShortDto(Pet pet);

    List<PetShortDto> toShortDtoList(List<Pet> pets);

    List<PetFullDto> toFullDtoList(List<Pet> pets);
}