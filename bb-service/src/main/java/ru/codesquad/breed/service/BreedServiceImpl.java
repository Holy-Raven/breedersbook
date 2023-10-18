package ru.codesquad.breed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.dto.BreedFullDto;
import ru.codesquad.breed.dto.BreedNewDto;
import ru.codesquad.breed.repository.BreedRepository;
import ru.codesquad.exception.NotFoundException;

import static ru.codesquad.breed.mapper.BreedMapper.returnBreed;
import static ru.codesquad.breed.mapper.BreedMapper.returnFullDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class BreedServiceImpl implements BreedService {
    private final BreedRepository repository;

    @Override
    public BreedFullDto add(BreedNewDto breedNewDto) {
        Breed breed = returnBreed(breedNewDto);
        breed = repository.save(breed);
        return returnFullDto(breed);
    }

    @Override
    public BreedFullDto getById(long breedId) {
        Breed breed = repository.findById(breedId)
                .orElseThrow(() -> new NotFoundException(Breed.class, String.format("Breed with id %d not found", breedId)));
        return returnFullDto(breed);
    }
}