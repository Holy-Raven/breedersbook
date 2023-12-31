package ru.codesquad.util;

import ru.codesquad.breed.Breed;
import ru.codesquad.club.Club;
import ru.codesquad.club.clubsusers.ClubsUsers;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.location.Location;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.user.User;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.enums.PetType;

public interface UnionService {
    User getUserOrNotFound(Long userId);

    User getUserOrNotFoundByAdmin(Long userId);

    UserInfo getUserInfoOrNotFound(Long userInfoId);

    Location getLocationOrNotFound(Long locationId);

    Kennel getKennelOrNotFound(Long kennelId);

    Breed getBreedOrNotFound(Long breedId);

    Pet getPetOrNotFound(Long petId);

    Club getClubOrNotFound(Long clubId);

    ClubsUsers getClubsUsersOrNotFound(Long clubId, Long userId);

    String checkPhoneNumber(String number);

    void checkOwner(Long userId, Long petId);

    void checkOwner(Long userId, Pet pet);

    PetType makePetType(String type);
}
