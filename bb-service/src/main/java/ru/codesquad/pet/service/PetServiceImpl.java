package ru.codesquad.pet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.pet.dto.NewPetDto;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.dto.UpdatePetDto;
import ru.codesquad.pet.enums.*;
import ru.codesquad.pet.mapper.PetMapper;
import ru.codesquad.pet.repository.PetRepository;
import ru.codesquad.util.enums.PetType;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository repository;
    private final PetMapper mapper;

    @Override
    public List<PetFullDto> getAllByUserId(long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public PetFullDto getUsersPetById(long userId, long petId) {
        return null;
    }

    @Override
    public PetFullDto add(long userId, NewPetDto newPetDto) {
        return null;
    }

    @Override
    public PetFullDto update(long userId, long petId, UpdatePetDto updatePetDto) {
        return null;
    }

    @Override
    public void delete(long userId, long petId) {

    }

    @Override
    public void delete(long petId) {

    }

    @Override
    public List<PetShortDto> getByFiltersPublic(PetType petType, Long breedId, FurType fur, CatPattern catPattern, DogPattern dogPattern, List<Color> colors, int priceFrom, Integer priceTo, PetSort sort, Integer from, Integer size, String ip) {
        return null;
    }

    @Override
    public PetShortDto getByIdPublic(long petId, String ip) {
        return null;
    }
}
