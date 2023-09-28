package ru.codesquad.pet.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.repository.BreedRepository;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.repository.CustomPetRepository;
import ru.codesquad.pet.repository.PetRepository;
import ru.codesquad.user.User;
import ru.codesquad.util.UnionService;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @Mock
    private PetRepository repository;

    @Mock
    private BreedRepository breedRepo;

    @Mock
    private UnionService unionService;

    @Mock
    private CustomPetRepository customRepo;

    @InjectMocks
    private PetServiceImpl service;

    private User owner;
    private Breed breed;
    private Kennel kennel;
    private Pet pet;


    @BeforeEach
    void setUp() {


    }

    @Test
    void getAllByUserId() {
        //Fail No Rights
        //Fail NotFound
        //Regular Case
    }

    @Test
    void getUsersPetById() {
        //Fail No Rights
        //Fail NotFound
        //Regular Case
    }

    @Test
    void shouldAdd() {
        //Regular Case
    }

    @Test
    void update() {
        //Fail No Rights
        //Fail NotFound
        //Regular Case
    }

    @Test
    void deleteByUser() {
        //Have
        //Regular Case
    }

    @Test
    void deleteByAdmin() {
        //Regular Case
    }

    @Test
    void getByFiltersPublic() {
    }

    @Test
    void getByIdPublic() {
        //NotFound
        //Regular Case
    }
}