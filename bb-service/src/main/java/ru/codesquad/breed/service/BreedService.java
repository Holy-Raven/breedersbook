package ru.codesquad.breed.service;

import ru.codesquad.breed.dto.BreedFullDto;
import ru.codesquad.breed.dto.BreedNewDto;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.util.enums.PetType;

import java.util.List;

public interface BreedService {
    BreedFullDto add(BreedNewDto breedNewDto);

    BreedFullDto getById(long breedId);

    List<BreedShortDto> getBreeders(Integer from, Integer size, PetType petType);
}
