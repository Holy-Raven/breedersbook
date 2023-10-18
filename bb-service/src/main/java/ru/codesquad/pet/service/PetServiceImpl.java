package ru.codesquad.pet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.breed.repository.BreedRepository;
import ru.codesquad.exception.ValidationException;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetNewDto;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.dto.PetUpdateDto;
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
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {

    private final PetRepository repository;
    private final UnionService unionService;
    private final CustomPetRepository customRepo;
    private final BreedRepository breedRepo;

    @Override
    public List<PetFullDto> getAllByUserId(long userId,
                                           Gender gender, SaleStatus saleStatus,
                                           PetSort petSort, Integer from, Integer size) {
        User owner = unionService.getUserOrNotFound(userId);
        PrivateSearchCriteria criteria = PrivateSearchCriteria.builder()
                .owner(owner)
                .gender(gender)
                .saleStatus(saleStatus)
                .sort(petSort)
                .from(from)
                .size(size)
                .build();
        List<Pet> pets = customRepo.getAllByCriteriaPrivate(criteria);
        return PetMapper.returnFullDtoList(pets);
    }

    @Override
    public PetFullDto getUsersPetById(long userId, long petId) {
        unionService.getUserOrNotFound(userId);
        Pet pet = unionService.getPetOrNotFound(petId);
        checkOwner(userId, pet);
        log.info("Get Pet by id {} for registered User with id {}", petId, userId);
        return PetMapper.returnFullDto(pet);
    }

    @Override
    @Transactional
    public PetFullDto add(long userId, PetNewDto petNewDto) {
        User owner = unionService.getUserOrNotFound(userId);
        Breed breed = unionService.getBreedOrNotFound(petNewDto.getBreedId());
        Pet pet = PetMapper.returnPet(petNewDto, owner, breed);
        pet = repository.save(pet);
        log.info("Pet with id {} was added to DB", pet.getId());
        return PetMapper.returnFullDto(pet);
    }

    @Override
    @Transactional
    public PetFullDto update(long userId, long petId, PetUpdateDto petUpdateDto) {
        unionService.getUserOrNotFound(userId);
        Pet pet = unionService.getPetOrNotFound(petId);
        checkOwner(userId, pet);
        updatePet(pet, petUpdateDto);
        pet = repository.save(pet);
        log.info("Pet with id {} was updated", pet.getId());
        return PetMapper.returnFullDto(pet);
    }

    @Override
    @Transactional
    public void deleteByUser(long userId, long petId) {
        if (isOwner(userId, petId)) {
            repository.deleteById(petId);
            log.info("Pet with id {} was deleted by owner with id {}", petId, userId);
        } else {
            log.warn("User with id {} is not owner of pet with id {}", userId, petId);
            throw new ValidationException(
                    String.format("User with id %d is not owner of pet with id %d", userId, petId));
        }
    }

    @Override
    @Transactional
    public void deleteByAdmin(long petId) {
        unionService.getPetOrNotFound(petId);
        log.info("Pet with id {} was deleted by admin", petId);
        repository.deleteById(petId);
    }

    @Override
    public List<PetShortDto> getByFiltersPublic(PetType petType, Gender gender,
                                                FurType fur, String pattern,
                                                List<Color> colors,
                                                int priceFrom, Integer priceTo,
                                                PetSort petSort, Integer from, Integer size,
                                                String ip) {
        List<Long> breedIds = breedRepo.findByFurType(fur).stream().map(Breed::getId).collect(Collectors.toList());
        PublicSearchCriteria criteria = PublicSearchCriteria.builder()
                .petType(petType)
                .breedIds(breedIds)
                .gender(gender)
                .pattern(pattern)
                .colors(colors)
                .priceFrom(priceFrom)
                .priceTo(priceTo)
                .petSort(petSort)
                .from(from)
                .size(size)
                .build();
        List<Pet> pets = customRepo.getAllByCriteriaPublic(criteria);
        return PetMapper.returnShortDtoList(pets);
    }

    @Override
    public PetShortDto getByIdPublic(long petId, String ip) {
        Pet pet = unionService.getPetOrNotFound(petId);
        log.info("Pet with id {} was asked by unregistered user", petId);
        return PetMapper.returnShortDto(pet);
    }

    private boolean isOwner(long userId, long petId) {
        unionService.getUserOrNotFound(userId);
        Pet pet = unionService.getPetOrNotFound(petId);
        return pet.getOwner().getId() == userId;
    }

    private void checkOwner(long userId, Pet pet) {
        if (pet.getOwner().getId() != userId) {
            log.warn("User with id {} is not owner of pet with id {}", userId, pet.getId());
            throw new ValidationException(
                    String.format("User with id %d is not owner of pet with id %d", userId, pet.getId()));
        }
    }

    private void updatePet(Pet pet, PetUpdateDto petUpdateDto) {
        if (petUpdateDto.getPetType() != null) {
            pet.setType(EnumUtil.getValue(PetType.class, petUpdateDto.getPetType()));
        }
        if (petUpdateDto.getName() != null) { //проверки размеров и непустоту в DTO
            pet.setName(petUpdateDto.getName());
        }
        if (petUpdateDto.getDescription() != null) {
            pet.setDescription(petUpdateDto.getDescription());
        }
        if (petUpdateDto.getGender() != null) {
            pet.setGender(EnumUtil.getValue(Gender.class, petUpdateDto.getGender()));
        }
        if (petUpdateDto.getPattern() != null) {
            if (pet.getType() == PetType.CAT) {
                EnumUtil.getValue(CatPattern.class, petUpdateDto.getPattern());
            } else {
                EnumUtil.getValue(DogPattern.class, petUpdateDto.getPattern());
            }
            pet.setPattern(petUpdateDto.getPattern());
        }
        if (petUpdateDto.getColors() != null) {
            repository.clearColors(pet.getId());
            List<Color> colors = petUpdateDto.getColors().stream()
                    .map(color -> EnumUtil.getValue(Color.class, color))
                    .collect(Collectors.toList());
            pet.setColors(colors);
        }
        if (petUpdateDto.getTemper() != null) {
            pet.setTemper(petUpdateDto.getTemper());
        }
        if (petUpdateDto.getBirthDate() != null) {
            pet.setBirthDate(petUpdateDto.getBirthDate());
        }
        if (petUpdateDto.getDeathDate() != null) {
            pet.setDeathDate(petUpdateDto.getDeathDate());
        }
        if (petUpdateDto.getPrice() != null) {
            pet.setPrice(petUpdateDto.getPrice());
        }
        if (petUpdateDto.getSaleStatus() != null) {
            pet.setSaleStatus(EnumUtil.getValue(SaleStatus.class, petUpdateDto.getSaleStatus()));
        }
        if (petUpdateDto.getBreedId() != null) {
            Breed breed = unionService.getBreedOrNotFound(petUpdateDto.getBreedId());
            pet.setBreed(breed);
        }
        if (petUpdateDto.getSterilized() != null) {
            pet.setSterilized(petUpdateDto.getSterilized());
        }
        if (petUpdateDto.getPassportImg() != null) {
            pet.setPassportImg(petUpdateDto.getPassportImg());
        }
    }
}