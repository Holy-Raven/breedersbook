package ru.codesquad.pet.repository;

import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.model.PrivateSearchCriteria;
import ru.codesquad.pet.model.PublicSearchCriteria;

import java.util.List;

public interface CustomPetRepository {
    List<Pet> getAllByCriteria(PrivateSearchCriteria criteria);

    List<Pet> getAllByCriteria(PublicSearchCriteria criteria);
}
