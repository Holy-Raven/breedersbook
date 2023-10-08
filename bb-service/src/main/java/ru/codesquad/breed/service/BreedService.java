package ru.codesquad.breed.service;

import ru.codesquad.breed.dto.BreedFullDto;
import ru.codesquad.breed.dto.BreedNewDto;

public interface BreedService {
    BreedFullDto add(BreedNewDto breedNewDto);

    BreedFullDto getById(long breedId);
}
