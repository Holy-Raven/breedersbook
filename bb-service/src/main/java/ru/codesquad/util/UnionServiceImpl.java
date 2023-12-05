package ru.codesquad.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.codesquad.breed.Breed;
import ru.codesquad.breed.repository.BreedRepository;
import ru.codesquad.club.Club;
import ru.codesquad.club.ClubRepository;
import ru.codesquad.club.clubsusers.ClubsUsers;
import ru.codesquad.club.clubsusers.ClubsUsersId;
import ru.codesquad.club.clubsusers.ClubsUsersRepository;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.exception.ValidationException;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.kennel.KennelRepository;
import ru.codesquad.location.Location;
import ru.codesquad.location.LocationRepository;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.repository.PetRepository;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.userinfo.UserInfoRepository;
import ru.codesquad.util.enums.Status;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnionServiceImpl implements UnionService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final LocationRepository locationRepository;
    private final KennelRepository kennelRepository;
    private final BreedRepository breedRepository;
    private final PetRepository petRepository;
    private final ClubRepository clubRepository;
    private final ClubsUsersRepository clubsUsersRepository;

    @Override
    public User getUserOrNotFound(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException(User.class, "User id " + userId + " not found.");
        } else {
            if (user.get().getStatus().equals(Status.DELETE)) {
                throw new NotFoundException(User.class, "User id " + userId + " not found.");
            }
            return user.get();
        }
    }

    @Override
    public User getUserOrNotFoundByAdmin(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException(User.class, "User id " + userId + " not found.");
        } else {
            return user.get();
        }
    }

    @Override
    public UserInfo getUserInfoOrNotFound(Long userInfoId) {

        Optional<UserInfo> userInfo = userInfoRepository.findById(userInfoId);

        if (userInfo.isEmpty()) {
            throw new NotFoundException(UserInfo.class, "UserInfo id " + userInfoId + " not found.");
        } else {
            return userInfo.get();
        }
    }

    @Override
    public Location getLocationOrNotFound(Long locationId) {

        Optional<Location> location = locationRepository.findById(locationId);

        if (location.isEmpty()) {
            throw new NotFoundException(Location.class, "Location id " + locationId + " not found.");
        } else {
            return location.get();
        }
    }

    @Override
    public Kennel getKennelOrNotFound(Long kennelId) {
        Optional<Kennel> kennel = kennelRepository.findById(kennelId);

        if (kennel.isEmpty()) {
            throw new NotFoundException(Kennel.class, "Kennel id " + kennelId + " not found.");
        } else {
            return kennel.get();
        }
    }

    @Override
    public Breed getBreedOrNotFound(Long breedId) {
        return breedRepository.findById(breedId)
                .orElseThrow(() -> new NotFoundException(Breed.class, String.format("Breed id %d not found.", breedId)));

    }

    @Override
    public Pet getPetOrNotFound(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new NotFoundException(Pet.class, String.format("Pet id %d not found.", petId)));
    }

    @Override
    public Club getClubOrNotFound(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(()-> new NotFoundException(Club.class, String.format("Club id %d not found.", clubId)));
    }

    @Override
    public ClubsUsers getClubsUsersOrNotFound(Long clubId, Long userId) {

        getClubOrNotFound(clubId);
        getUserOrNotFound(userId);

        ClubsUsersId clubsUsersId = new ClubsUsersId(clubId, userId);

        return clubsUsersRepository.findById(clubsUsersId).orElseThrow(()-> new NotFoundException(ClubsUsers.class, String.format("ClubsUsers club %d, user %d not found", clubId, userId)));
    }

    @Override
    public String checkPhoneNumber(String number) {

        number = number.replaceAll("[^0-9]", "");

        if (number.length() == 10) {
            return "+7" + number;
        }
        if (number.length() == 11) {
            if (number.charAt(0) == '8') {
                return "+7" + number.substring(number.indexOf('8') + 1);
            } else if (number.charAt(0) == '7') {
                return "+" + number;
            } else if (number.charAt(0) != '7' || number.charAt(0) != '8') {
                throw new ValidationException("Invalid number format");
            }
        }
        throw new ValidationException("Invalid number format");
    }

    @Override
    public void checkOwner(Long userId, Long petId) {
        getUserOrNotFound(userId);
        Pet pet = getPetOrNotFound(petId);
        if (!pet.getOwner().getId().equals(userId)) {
            log.warn("User with id {} is not owner of pet with id {}", userId, pet.getId());
            throw new ValidationException(
                    String.format("User with id %d is not owner of pet with id %d", userId, pet.getId()));
        }
    }

    @Override
    public void checkOwner(Long userId, Pet pet) {
        getUserOrNotFound(userId);
        if (!pet.getOwner().getId().equals(userId)) {
            log.warn("User with id {} is not owner of pet with id {}", userId, pet.getId());
            throw new ValidationException(
                    String.format("User with id %d is not owner of pet with id %d", userId, pet.getId()));
        }
    }
}