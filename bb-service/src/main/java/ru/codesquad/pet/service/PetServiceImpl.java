package ru.codesquad.pet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.breed.repository.BreedRepository;
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
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository repository;
    private final UnionService unionService;
    private final CustomPetRepository customRepo;
    private final BreedRepository breedRepo;


    private final PetMapper mapper;

    @Override
    public List<PetFullDto> getAllByUserId(long userId,
                                           Gender gender, SaleStatus saleStatus,
                                           PetSort petSort, Integer from, Integer size) {
        unionService.getUserOrNotFound(userId);
        PrivateSearchCriteria criteria = PrivateSearchCriteria.builder()
                .userId(userId)
                .gender(gender)
                .saleStatus(saleStatus)
                .sort(petSort)
                .build();
        List<Pet> pets = customRepo.getAllByCriteria(criteria);
        return mapper.toFullDto(pets);
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
        Breed breed = getBreedOrNotFount(newPetDto.getBreedId());
        Pet pet = mapper.toPet(newPetDto, owner, breed);
        pet = repository.save(pet);
        return mapper.toFullDto(pet);
    }

    @Override
    public PetFullDto update(long userId, long petId, UpdatePetDto updatePetDto) {
        unionService.getUserOrNotFound(userId);
        Pet pet = getPetOrNotFound(petId);
        checkOwner(userId, pet);
        updatePet(pet, updatePetDto);
        pet = repository.save(pet);
        return mapper.toFullDto(pet);
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
        return mapper.toShortDto(pets);
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

    private Breed getBreedOrNotFount(long breedId) {
        return breedRepo.findById(breedId)
                .orElseThrow(() -> new NotFoundException(Pet.class, "Breed with id " + breedId + " not found."));

    }

    private void updatePet(Pet pet, UpdatePetDto updatePetDto) {
        if (updatePetDto.getPetType() != null) {
            pet.setType(EnumUtil.getValue(PetType.class, updatePetDto.getPetType()));
        }
        if (updatePetDto.getName() != null) {
            pet.setName(updatePetDto.getName());
        }
        if (updatePetDto.getDescription() != null) {
            pet.setDescription(updatePetDto.getDescription());
        }
        if (updatePetDto.getGender() != null) {
            pet.setGender(EnumUtil.getValue(Gender.class, updatePetDto.getGender()));
        }
        if (updatePetDto.getPattern() != null) {
            if (pet.getType() == PetType.CAT) {
                EnumUtil.getValue(CatPattern.class, updatePetDto.getPattern());
            } else {
                EnumUtil.getValue(DogPattern.class, updatePetDto.getPattern());
            }
            pet.setPattern(updatePetDto.getPattern());
        }
        if (updatePetDto.getColors() != null) {
            //удалить старые цвета из таблиц
            List<Color> colors = updatePetDto.getColors().stream()
                    .map(color -> EnumUtil.getValue(Color.class, color))
                    .collect(Collectors.toList());
            pet.setColors(colors);
        }
        if (updatePetDto.getTemper() != null) {
            pet.setTemper(updatePetDto.getTemper());
        }
        if (updatePetDto.getBirthDate() != null) {
            pet.setBirthDate(updatePetDto.getBirthDate());
        }
        if (updatePetDto.getDeathDate() != null) {
            pet.setDeathDate(updatePetDto.getDeathDate());
        }
        if (updatePetDto.getPrice() != null) {
            pet.setPrice(updatePetDto.getPrice());
        }
        if (updatePetDto.getSaleStatus() != null) {
            pet.setSaleStatus(EnumUtil.getValue(SaleStatus.class, updatePetDto.getSaleStatus()));
        }
        if (updatePetDto.getBreedId() != null) {
            Breed breed = getBreedOrNotFount(updatePetDto.getBreedId());
            pet.setBreed(breed);
        }
        if (updatePetDto.getSterilized() != null) {
            pet.setSterilized(updatePetDto.getSterilized());
        }
        if (updatePetDto.getPassportImg() != null) {
            pet.setPassportImg(updatePetDto.getPassportImg());
        }
    }
}