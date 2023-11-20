package ru.codesquad.club;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.breed.Breed;
import ru.codesquad.club.clubsusers.ClubsUsers;
import ru.codesquad.club.clubsusers.ClubsUsersRepository;
import ru.codesquad.club.dto.ClubDto;
import ru.codesquad.club.dto.ClubMapper;
import ru.codesquad.club.dto.ClubNewDto;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.repository.PetRepository;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.ClubRole;
import ru.codesquad.util.enums.Status;

import java.util.List;

import static ru.codesquad.util.Constant.CURRENT_TIME;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final UnionService unionService;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ClubsUsersRepository clubsUsersRepository;

    @Override
    public ClubDto addClub(Long yourId, ClubNewDto clubNewDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Breed clubBreed = unionService.getBreedOrNotFound(clubNewDto.getBreedId());

        List<Pet> userPet = petRepository.findByOwnerId(yourId);

        boolean flagType = false;
        for (Pet pet : userPet) {
            if (pet.getType().equals(clubBreed.getPetType())) {
                flagType = true;
                break;
            }
        }

        boolean flagBreed = false;
        for (Pet pet : userPet) {
            if (pet.getBreed().equals(clubBreed)) {
                flagBreed = true;
                break;
            }
        }

        if (userPet.isEmpty()) {
            throw new ConflictException("У юзера нет питомца, только юзера владеющие питомцем могут создавать клуб");
        } else if (!flagType) {
            throw new ConflictException("Тип питомца юзера не совпадаент с типом животных заявленной в клубе");
        } else if (!flagBreed) {
            throw new ConflictException("Порода питомца юзера не совпадаент с породой заявленной в клубе");
        }

        Club club = ClubMapper.returnClub(clubNewDto, clubBreed);
        club = clubRepository.save(club);
        userRepository.save(user);

        ClubsUsers clubsUsers = ClubsUsers.builder()
                .userId(user.getId())
                .clubId(club.getId())
                .created(CURRENT_TIME)
                .status(Status.ACTIVE)
                .role(ClubRole.ADMIN)
                .build();

        clubsUsersRepository.save(clubsUsers);

        return ClubMapper.returnClubDto(club);
    }
}
