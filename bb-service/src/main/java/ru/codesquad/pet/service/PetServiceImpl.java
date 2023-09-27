package ru.codesquad.pet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.exception.ValidationException;
import ru.codesquad.pet.dto.NewPetDto;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.dto.UpdatePetDto;
import ru.codesquad.pet.enums.*;
import ru.codesquad.pet.mapper.PetMapper;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.model.PrivateSearchCriteria;
import ru.codesquad.pet.model.PublicSearchCriteria;
import ru.codesquad.pet.repository.CustomPetRepository;
import ru.codesquad.pet.repository.PetRepository;
import ru.codesquad.user.User;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository repository;
    private final UnionService unionService;
    private final CustomPetRepository customRepo;

    private final PetMapper mapper;

    @Override
    public List<PetFullDto> getAllByUserId(long userId,
                                           Gender gender, SaleStatus saleStatus,
                                           PetSort petSort, Integer from, Integer size) {
        User user = unionService.getUserOrNotFound(userId);
        //Sort sort = petSort == null ? sortMap.get(PetSort.DEFAULT) : sortMap.get(petSort);
        PrivateSearchCriteria criteria = PrivateSearchCriteria.builder()
                .userId(userId)
                .gender(gender)
                .saleStatus(saleStatus)
                .sort(petSort)
                .build();
        List<Pet> pets = customRepo.getAllByCriteria(criteria);
        return null;
    }

    @Override
    public PetFullDto getUsersPetById(long userId, long petId) {
        unionService.getUserOrNotFound(userId);
        Pet pet = getPetOrNotFound(petId);
        checkOwner(userId, pet);
        return mapper.toFullDto(pet);
    }

    @Override
    public PetFullDto add(long userId, NewPetDto newPetDto) {
        User owner = unionService.getUserOrNotFound(userId);
        Pet pet = mapper.toPet(newPetDto, owner);
        pet = repository.save(pet);
        return mapper.toFullDto(pet);
    }

    @Override
    public PetFullDto update(long userId, long petId, UpdatePetDto updatePetDto) {
        User owner = unionService.getUserOrNotFound(userId);
        Pet pet = getPetOrNotFound(petId);
        checkOwner(userId, pet);
        pet = updatePet(pet, updatePetDto);
        pet = repository.save(pet);
        return mapper.toFullDto(pet);
    }

    private Pet updatePet(Pet pet, UpdatePetDto updatePetDto) {
        return pet;
    }

    @Override
    public void deleteByUser(long userId, long petId) {
        if (isOwner(userId, petId)) {
            repository.deleteById(petId);
        } else {
            log.warn("User with id {} is not owner of pet with id {}", userId, petId);
            throw new ValidationException(
                    String.format("User with id %d is not owner of pet with id %d", userId, petId));
        }
    }

    @Override
    public void deleteByAdmin(long petId) {
        repository.deleteById(petId);
    }

    @Override
    public List<PetShortDto> getByFiltersPublic(PetType petType, Long breedId,
                                                FurType fur, CatPattern catPattern, DogPattern dogPattern,
                                                List<Color> colors,
                                                int priceFrom, Integer priceTo,
                                                PetSort petSort, Integer from, Integer size,
                                                String ip) {
        //Sort sort = petSort == null ? sortMap.get(PetSort.DEFAULT) : sortMap.get(petSort);
        PublicSearchCriteria criteria = PublicSearchCriteria.builder()
                .build();
        List<Pet> pets = customRepo.getAllByCriteria(criteria);
        return null;
    }

    @Override
    public PetShortDto getByIdPublic(long petId, String ip) {
        Pet pet = getPetOrNotFound(petId);
        return mapper.toShortDto(pet);
    }

    private boolean isOwner(long userId, long petId) {
        unionService.getUserOrNotFound(userId);
        Pet pet = getPetOrNotFound(petId);
        return pet.getOwner().getId() == userId;
    }

    private void checkOwner(long userId, Pet pet) {
        if (pet.getOwner().getId() != userId) {
            log.warn("User with id {} is not owner of pet with id {}", userId, pet.getId());
            throw new ValidationException(
                    String.format("User with id %d is not owner of pet with id %d", userId, pet.getId()));
        }
    }

    private Pet getPetOrNotFound(long petId) {
        return repository.findById(petId)
                .orElseThrow(() -> new NotFoundException(Pet.class, "Pet with id " + petId + " not found."));
    }
}