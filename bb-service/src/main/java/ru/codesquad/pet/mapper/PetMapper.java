package ru.codesquad.pet.mapper;

import ru.codesquad.breed.Breed;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetNewDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.user.User;

import java.util.List;


public interface PetMapper {
    Pet returnPet(PetNewDto petNewDto, User owner, Breed breed);

    PetFullDto returnFullDto(Pet pet);

    PetShortDto returnShortDto(Pet pet);

    List<PetShortDto> returnShortDtoList(List<Pet> pets);

    List<PetFullDto> returnFullDtoList(List<Pet> pets);
}