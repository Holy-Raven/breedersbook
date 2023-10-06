package ru.codesquad.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.pet.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
