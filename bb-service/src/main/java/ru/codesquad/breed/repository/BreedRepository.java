package ru.codesquad.breed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.enums.FurType;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
    List<Breed> findByFurType(FurType furType);

    Breed findByName(String name);

    Page<Breed> findByFurType(FurType furType, PageRequest page);
}
