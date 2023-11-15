package ru.codesquad.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.model.Pet;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(nativeQuery = true, value = "delete from pets_colors where pet_id = :petId")
    void clearColors(@Param(value = "petId") long petId);

    List<Pet> findByOwnerId(long ownerId);
}
