package ru.codesquad.pet.service;

import ru.codesquad.breed.enums.FurType;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetNewDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.dto.PetUpdateDto;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.util.List;

public interface PetService {
    List<PetFullDto> getAllByUserId(long userId, Gender gender, SaleStatus saleStatus, PetSort sort, Integer from, Integer size);

    PetFullDto getUsersPetById(long userId, long petId);

    PetFullDto add(long userId, PetNewDto petNewDto);

    PetFullDto update(long userId, long petId, PetUpdateDto petUpdateDto);

    void deleteByUser(long userId, long petId);

    void deleteByAdmin(long petId);

    List<PetShortDto> getByFiltersPublic(PetType petType, Gender gender, FurType fur, String pattern, List<Color> colors, int priceFrom, Integer priceTo, PetSort sort, Integer from, Integer size, String ip);

    PetShortDto getByIdPublic(long petId, String ip);
}
