package ru.codesquad.pet.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.breed.repository.BreedRepository;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.exception.ValidationException;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetNewDto;
import ru.codesquad.pet.dto.PetUpdateDto;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.pet.mapper.PetMapper;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.model.PrivateSearchCriteria;
import ru.codesquad.pet.repository.CustomPetRepository;
import ru.codesquad.pet.repository.PetRepository;
import ru.codesquad.user.User;
import ru.codesquad.user.dto.UserShortDto;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.codesquad.pet.util.TestFactory.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class PetServiceTest {
    @Mock
    private PetRepository repository;

    @Mock
    private PetMapper mapper;

    @Mock
    private BreedRepository breedRepo;

    @Mock
    private UnionService unionService;

    @Mock
    private CustomPetRepository customRepo;

    @InjectMocks
    private PetServiceImpl service;

    private User owner;
    private User user;
    private Breed breed;
    private Pet pet;

    PetFullDto petFullDto;
    PetNewDto petNewDto;
    PetUpdateDto petUpdateDto;
    UserShortDto ownerDto;
    UserInfoDto ownerInfoDto;
    BreedShortDto breedDto;
    KennelDto kennelDto;

    PetFullDto.PetFullDtoBuilder fullDtoBuilder;
    PetNewDto.PetNewDtoBuilder newDtoBuilder;
    PetUpdateDto.PetUpdateDtoBuilder updateDtoBuilder;


    @BeforeEach
    void setUp() {
        ownerInfoDto = UserInfoDto.builder()
                .birthDate(LocalDateTime.now().minusYears(30))
                .description("Description1")
                .phone("+79001234561")
                .photo("https://photo-url1.com")
                .build();
        UserInfo ownerInfo = makeNewUserInfo(1);
        ownerInfo.setId(1L);
        UserInfo userInfo = makeNewUserInfo(2);
        userInfo.setId(2L);
        ownerDto = UserShortDto.builder()
                .name("Name")
                .gender(Gender.FEMALE)
                .userInfo(ownerInfoDto)
                .build();
        breedDto = BreedShortDto.builder()
                .id(1L)
                .name("NoName")
                .build();
        breed = makeNewBreed();
        breed.setId(1L);
        Kennel kennel = makeNewKennel(1);
        kennel.setId(1L);
        kennelDto = KennelDto.builder()
                .id(1L)
                .name("Name")
                .build();
        fullDtoBuilder = PetFullDto.builder()
                .id(1L)
                .owner(ownerDto)
                .saleStatus(SaleStatus.NOT_FOR_SALE)
                .temper("Angry small evil")
                .petType(PetType.CAT)
                .colors(List.of(Color.BLACK))
                .pattern("SOLID")
                .breed(breedDto)
                .name("Name1")
                .description("Description1")
                .sterilized(false)
                .passportImg("https://passport-url1.com")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.now().minusMonths(1))
                .kennel(kennelDto);
        newDtoBuilder = PetNewDto.builder()
                .petType("cat")
                .breedId(1L)
                .gender("female")
                .pattern("solid")
                .colors(List.of("black"))
                .birthDate(LocalDate.now().minusMonths(1))
                .name("Name1")
                .description("Description1")
                .forSale(false)
                .passportImg("https://passport-url1.com")
                .temper("Angry small evil");
        owner = makeNewUser(ownerInfo, kennel, 1);
        owner.setId(1L);
        user = makeNewUser(userInfo, kennel, 2);
        user.setId(2L);

        pet = makeNewCat(breed, owner, kennel, 1);
        pet.setId(1L);

        updateDtoBuilder = PetUpdateDto.builder()
                .description("DescriptionUpdate")
                .saleStatus("for_sale")
                .colors(List.of("black", "white"));
    }

    @Test
    void shouldFailGetAllByUserId() {
        //Fail NotFound
        petFullDto = fullDtoBuilder.build();
        long userIdNotFound = 3L;
        when(unionService.getUserOrNotFound(userIdNotFound))
                .thenThrow(new NotFoundException(User.class, "User Not Found"));
        String error = "Entity User not found. User Not Found";
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> service.getAllByUserId(userIdNotFound, null, null, null, 0, 10)
        );
        assertEquals(error, exception.getMessage());
    }

    @Test
    void shouldGetAllByUserId() {
        //Empty List
        long ownerId = owner.getId();
        when(unionService.getUserOrNotFound(ownerId)).thenReturn(owner);
        PrivateSearchCriteria criteria = PrivateSearchCriteria.builder()
                .owner(owner)
                .gender(Gender.MALE)
                .saleStatus(SaleStatus.NOT_FOR_SALE)
                .sort(PetSort.PRICE_ASC)
                .build();
        when(customRepo.getAllByCriteriaPrivate(criteria)).thenReturn(Collections.emptyList());
        when(mapper.toFullDtoList(anyList())).thenReturn(Collections.emptyList());

        List<PetFullDto> pets = service
                .getAllByUserId(ownerId, Gender.MALE, SaleStatus.NOT_FOR_SALE, PetSort.PRICE_ASC, 0, 10);
        assertNotNull(pets);

        //Single List

    }

    @Test
    void shouldFailGetUsersPetById() {
        //Fail No Rights
        petFullDto = fullDtoBuilder.build();
        long userId = user.getId();
        long petId = pet.getId();
        when(unionService.getUserOrNotFound(userId)).thenReturn(user);
        when(repository.findById(petId)).thenReturn(Optional.of(pet));
        String error = String.format("User with id %d is not owner of pet with id %d", userId, petId);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> service.getUsersPetById(userId, petId)
        );
        assertEquals(error, exception.getMessage());

        //Fail NotFound User
        long userIdNotFound = 3L;
        when(unionService.getUserOrNotFound(userIdNotFound))
                .thenThrow(new NotFoundException(User.class, "User Not Found"));
        assertThrows(
                NotFoundException.class,
                () -> service.getUsersPetById(userIdNotFound, petId)
        );

        //Fail NotFound Pet
        long petIdNotFound = 3L;
        when(repository.findById(petIdNotFound)).thenReturn(Optional.empty());
        assertThrows(
                NotFoundException.class,
                () -> service.getUsersPetById(userId, petIdNotFound)
        );
    }

    @Test
    void shouldGetUsersPetById() {
        //Regular Case
        petFullDto = fullDtoBuilder.build();
        //pet = petBuilder.build();
        long ownerId = owner.getId();
        long petId = pet.getId();
        when(unionService.getUserOrNotFound(ownerId)).thenReturn(owner);
        when(repository.findById(petId)).thenReturn(Optional.of(pet));
        when(mapper.toFullDto(pet)).thenReturn(petFullDto);

        PetFullDto petFromService = service.getUsersPetById(ownerId, petId);
        assertNotNull(petFromService);
        assertEquals(petFullDto, petFromService);
    }

    @Test
    void shouldFailAdd() {
        petNewDto = newDtoBuilder.build();
        when(unionService.getUserOrNotFound(anyLong()))
                .thenThrow(new NotFoundException(User.class, "User Not Found"));
        assertThrows(
                NotFoundException.class,
                () -> service.add(user.getId(), petNewDto)
        );
    }

    @Test
    void shouldAdd() {
        //Regular Case
        petNewDto = newDtoBuilder.build();
        //pet = petBuilder.build();
        petFullDto = fullDtoBuilder.build();
        when(unionService.getUserOrNotFound(anyLong())).thenReturn(owner);
        when(breedRepo.findById(anyLong())).thenReturn(Optional.of(breed));
        when(repository.save(any())).thenReturn(pet);
        when(mapper.toPet(petNewDto, owner, breed)).thenReturn(pet);
        when(mapper.toFullDto(pet)).thenReturn(petFullDto);

        PetFullDto petAdded = service.add(anyLong(), petNewDto);

        assertNotNull(petAdded);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldFailUpdate() {
        //Fail No Rights
        petFullDto = fullDtoBuilder.build();
        petUpdateDto = updateDtoBuilder.build();
        long userId = user.getId();
        long petId = pet.getId();
        when(unionService.getUserOrNotFound(userId)).thenReturn(user);
        when(repository.findById(petId)).thenReturn(Optional.of(pet));
        String error = String.format("User with id %d is not owner of pet with id %d", userId, petId);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> service.update(userId, petId, petUpdateDto)
        );
        assertEquals(error, exception.getMessage());

        //Fail NotFound User
        long userIdNotFound = 3L;
        when(unionService.getUserOrNotFound(userIdNotFound))
                .thenThrow(new NotFoundException(User.class, "User Not Found"));
        assertThrows(
                NotFoundException.class,
                () -> service.update(userIdNotFound, petId, petUpdateDto)
        );

        //Fail NotFound Pet
        long petIdNotFound = 3L;
        when(repository.findById(petIdNotFound)).thenReturn(Optional.empty());
        assertThrows(
                NotFoundException.class,
                () -> service.update(userId, petIdNotFound, petUpdateDto)
        );
    }

    @Test
    void shouldUpdate() {
        //Regular Case
        petUpdateDto = updateDtoBuilder.build();
        //pet = petBuilder.build();
        petFullDto = fullDtoBuilder
                .description("DescriptionUpdate")
                .saleStatus(SaleStatus.FOR_SALE)
                .colors(List.of(Color.BLACK, Color.WHITE))
                .build();

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(owner);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(pet));
        Pet petUpdated = pet;
        petUpdated.setDescription("DescriptionUpdate");
        petUpdated.setSaleStatus(SaleStatus.FOR_SALE);
        petUpdated.setColors(List.of(Color.BLACK, Color.WHITE));

        when(repository.save(any())).thenReturn(petUpdated);
        when(mapper.toFullDto(petUpdated)).thenReturn(petFullDto);

        PetFullDto petFullDtoUpdated = service.update(owner.getId(), pet.getId(), petUpdateDto);
        assertNotNull(petFullDtoUpdated);

        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldFailDeleteByUser() {
        //Fail No Rights
        petFullDto = fullDtoBuilder.build();
        long userId = user.getId();
        long petId = pet.getId();
        when(unionService.getUserOrNotFound(userId)).thenReturn(user);
        when(repository.findById(petId)).thenReturn(Optional.of(pet));
        String error = String.format("User with id %d is not owner of pet with id %d", userId, petId);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> service.deleteByUser(userId, petId)
        );
        assertEquals(error, exception.getMessage());

        //Fail NotFound User
        long userIdNotFound = 3L;
        when(unionService.getUserOrNotFound(userIdNotFound))
                .thenThrow(new NotFoundException(User.class, "User Not Found"));
        assertThrows(
                NotFoundException.class,
                () -> service.deleteByUser(userIdNotFound, petId)
        );

        //Fail NotFound Pet
        long petIdNotFound = 3L;
        when(repository.findById(petIdNotFound)).thenReturn(Optional.empty());
        assertThrows(
                NotFoundException.class,
                () -> service.deleteByUser(userId, petIdNotFound)
        );
    }

    @Test
    void shouldDeleteByUser() {
        //Regular Case
        petFullDto = fullDtoBuilder.build();
        long ownerId = owner.getId();
        long petId = pet.getId();
        when(unionService.getUserOrNotFound(ownerId)).thenReturn(owner);
        when(repository.findById(petId)).thenReturn(Optional.of(pet));
        doNothing().when(repository).deleteById(anyLong());
        service.deleteByUser(ownerId, petId);

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void shouldFailDeleteByAdmin() {
        //NotFound
        long petId = pet.getId();
        when(repository.findById(petId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> service.deleteByAdmin(petId)
        );

        assertEquals(exception.getMessage(), "Entity Pet not found. Pet with id 1 not found.");
    }

    @Test
    void shouldDeleteByAdmin() {
        //Regular Case
        long petId = pet.getId();
        when(repository.findById(petId)).thenReturn(Optional.of(pet));
        doNothing().when(repository).deleteById(anyLong());
        service.deleteByAdmin(petId);

        verify(repository, times(1)).deleteById(anyLong());
    }

    ////////////////ЗАГОТОВКИ ДЛЯ ПУБЛИЧНЫХ МЕТОДОВ//////////////
    @Test
    void getByFiltersPublic() {
        //Заготовка для публичных методов
        //Regular Case
    }

    @Test
    void getByIdPublic() {
        //Заготовка для публичных методов
        //NotFound
        //Regular Case
    }
}