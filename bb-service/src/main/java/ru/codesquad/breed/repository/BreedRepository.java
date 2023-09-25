package ru.codesquad.breed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.breed.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
}
