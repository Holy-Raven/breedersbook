package ru.codesquad.pet.repository;

import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.model.PrivateSearchCriteria;
import ru.codesquad.pet.model.PublicSearchCriteria;

import java.util.List;

public interface CustomPetRepository {
    List<Pet> getAllByCriteriaPrivate(PrivateSearchCriteria criteria);

    List<Pet> getAllByCriteriaPublic(PublicSearchCriteria criteria);
}
