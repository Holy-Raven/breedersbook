package ru.codesquad.pet.service;

import ru.codesquad.breed.enums.FurType;
import ru.codesquad.pet.dto.NewPetDto;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.dto.UpdatePetDto;
import ru.codesquad.pet.enums.*;

import java.util.List;

public interface PetService {
    List<PetFullDto> getAllByUserId(long userId, Integer from, Integer size);

    PetFullDto getUsersPetById(long userId, long petId);

    PetFullDto add(long userId, NewPetDto newPetDto);

    PetFullDto update(long userId, long petId, UpdatePetDto updatePetDto);

    void delete(long userId, long petId);

    void delete(long petId);

    List<PetShortDto> getByFiltersPublic(PetType petType, Long breedId, FurType fur, CatPattern catPattern, DogPattern dogPattern, List<Color> colors, int priceFrom, Integer priceTo, PetSort sort, Integer from, Integer size, String ip);

    PetShortDto getByIdPublic(long petId, String ip);
}
