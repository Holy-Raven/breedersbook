package ru.codesquad.util;

import ru.codesquad.breed.Breed;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.location.Location;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.UserInfo;

public interface UnionService {
    User getUserOrNotFound(Long userId);

    User getUserOrNotFoundByAdmin(Long userId);

    UserInfo getUserInfoOrNotFound(Long userInfoId);

    Location getLocationOrNotFound(Long locationId);

    Kennel getKennelOrNotFound(Long kennelId);

    Breed getBreedOrNotFound(Long breedId);

    Pet getPetOrNotFound(Long petId);

    String checkPhoneNumber(String number);
}
