package ru.codesquad.breed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.util.enums.PetType;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
    Breed findByName(String name);

//    List<Breed> findByFurType(FurType furType);
    List<Breed> findByPetType(PetType petType, PageRequest page);
    Page<Breed> findByFurType(FurType furType, PageRequest page);
}
