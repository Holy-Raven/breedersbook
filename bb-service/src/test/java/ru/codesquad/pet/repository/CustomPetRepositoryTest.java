package ru.codesquad.pet.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.repository.BreedRepository;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.kennel.KennelRepository;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.model.PrivateSearchCriteria;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.userinfo.UserInfoRepository;
import ru.codesquad.util.enums.Gender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.codesquad.util.TestFactory.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
class CustomPetRepositoryTest {

    private final CustomPetRepository repository;
    private final UserRepository userRepo;
    private final BreedRepository breedRepo;
    private final PetRepository petRepo;
    private final UserInfoRepository userInfoRepo;
    private final KennelRepository kennelRepo;


    private PrivateSearchCriteria criteria;
    private PrivateSearchCriteria.PrivateSearchCriteriaBuilder criteriaBuilder;

    private User user;
    private User owner;
    private Pet pet1;
    private Pet pet2;

    @BeforeEach
    void setup() {
        Breed breed = makeNewBreed();
        breed = breedRepo.save(breed);

        UserInfo ownerInfo = makeNewUserInfo(1);
        ownerInfo = userInfoRepo.save(ownerInfo);
        UserInfo userInfo = makeNewUserInfo(2);
        userInfo = userInfoRepo.save(userInfo);

        Kennel kennel1 = makeNewKennel(1);
        kennel1 = kennelRepo.save(kennel1);
        Kennel kennel2 = makeNewKennel(2);
        kennel2 = kennelRepo.save(kennel2);

        owner = makeNewUser(ownerInfo, kennel1, 1);
        owner = userRepo.save(owner);
        user = makeNewUser(userInfo, kennel2, 2);
        user = userRepo.save(user);

        pet1 = makeNewCat(breed, owner, kennel1, 1);
        pet1 = petRepo.save(pet1);
        pet2 = makeNewCat(breed, owner, kennel1, 2);
        pet2.setGender(Gender.MALE);
        pet2.setSaleStatus(SaleStatus.FOR_SALE);
        pet2.setPrice(5_000);
        pet2 = petRepo.save(pet2);

        criteriaBuilder = PrivateSearchCriteria.builder()
                .sort(PetSort.DEFAULT)
                .from(0)
                .size(10);
    }

    @Test
    void shouldGetAllByCriteriaPrivateEmptyList() {
        //Empty List — No Pets
        criteria = criteriaBuilder
                .owner(user)
                .build();
        List<Pet> pets = repository.getAllByCriteriaPrivate(criteria);
        assertNotNull(pets);
        assertEquals(0, pets.size());

        //Empty List — No Relevant SaleStatus Pets
        criteria = criteriaBuilder
                .owner(owner)
                .gender(Gender.FEMALE)
                .saleStatus(SaleStatus.FOR_SALE)
                .build();
        pets = repository.getAllByCriteriaPrivate(criteria);
        assertNotNull(pets);
        assertEquals(0, pets.size());

        //Empty List — No Relevant SaleStatus Pets
        criteria = criteriaBuilder
                .owner(owner)
                .gender(Gender.MALE)
                .saleStatus(SaleStatus.NOT_FOR_SALE)
                .build();
        pets = repository.getAllByCriteriaPrivate(criteria);
        assertNotNull(pets);
        assertEquals(0, pets.size());
    }

    @Test
    void shouldGetAllByCriteriaPrivateSingleList() {
        //Single List
        criteria = criteriaBuilder
                .owner(owner)
                .gender(Gender.FEMALE)
                .build();
        List<Pet> pets = repository.getAllByCriteriaPrivate(criteria);
        assertNotNull(pets);
        assertEquals(1, pets.size());
        assertEquals(Gender.FEMALE, pets.get(0).getGender());

        //Single List
        criteria = criteriaBuilder
                .owner(owner)
                .gender(Gender.MALE)
                .build();
        pets = repository.getAllByCriteriaPrivate(criteria);
        assertNotNull(pets);
        assertEquals(1, pets.size());
        assertEquals(Gender.MALE, pets.get(0).getGender());
    }

    @Test
    void shouldGetAllByCriteriaPrivateSortedList() {
        //Sorting By Id
        criteria = criteriaBuilder
                .owner(owner)
                .build();
        List<Pet> pets = repository.getAllByCriteriaPrivate(criteria);
        assertNotNull(pets);
        assertEquals(2, pets.size());
        assertEquals(Gender.FEMALE, pets.get(0).getGender());

        //Sorting By Age
        criteria = criteriaBuilder
                .owner(owner)
                .sort(PetSort.AGE_DESC)
                .build();
        pets = repository.getAllByCriteriaPrivate(criteria);
        assertNotNull(pets);
        assertEquals(2, pets.size());
        assertEquals(Gender.MALE, pets.get(0).getGender());
    }

    @Test
    void shouldGetAllByCriteriaPublic() {
        //заготовка для теста публичного метода
    }
}