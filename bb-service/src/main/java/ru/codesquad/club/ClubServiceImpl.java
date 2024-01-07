package ru.codesquad.club;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.breed.Breed;
import ru.codesquad.club.clubsusers.*;
import ru.codesquad.club.clubsusers.dto.ClubsUsersMapper;
import ru.codesquad.club.clubsusers.dto.ClubsUsersShortDto;
import ru.codesquad.club.dto.*;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.exception.ForbiddenException;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.repository.PetRepository;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.user.dto.UserMapper;
import ru.codesquad.user.dto.UserShortDto;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.ClubRole;
import ru.codesquad.util.enums.PetType;
import ru.codesquad.util.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
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
                .created(LocalDate.from(CURRENT_TIME))
                .status(Status.ACTIVE)
                .role(ClubRole.ADMIN)
                .build();
        clubsUsersRepository.save(clubsUsers);

        ClubDto clubDto = ClubMapper.returnClubDto(club);
        clubDto.setSubscribersCount(clubsUsersRepository.subscribersCount(club.getId()));
        System.out.println();
        System.out.println(clubDto);
        System.out.println();
        return clubDto;
    }

    @Override
    @Transactional
    public ClubsUsersShortDto joinInClub(Long yourId, Long clubId) {

        Optional<ClubsUsers> optionalClubsUsers = clubsUsersRepository.findById(new ClubsUsersId(clubId, yourId));

        if (optionalClubsUsers.isPresent() && !optionalClubsUsers.get().getStatus().equals(Status.DELETE)) {
                throw new ConflictException(String.format("user id %d уже является членом club id %d.", yourId, clubId));
        }

        ClubsUsers clubsUsers = ClubsUsers.builder()
                .userId(yourId)
                .clubId(clubId)
                .role(ClubRole.USER)
                .status(Status.ACTIVE)
                .created(LocalDate.from(CURRENT_TIME))
                .build();

        clubsUsers = clubsUsersRepository.save(clubsUsers);
        return ClubsUsersMapper.returnClubsUsersShortDto(clubsUsers);
    }

    @Override
    @Transactional
    public Boolean kickOutOfClub(Long clubId, Long yourId, Long userId) {

        unionService.getClubOrNotFound(clubId);
        unionService.getUserOrNotFound(yourId);
        unionService.getUserOrNotFound(userId);

        ClubsUsers clubsUsersAdmin = unionService.getClubsUsersOrNotFound(clubId, yourId);
        if (!clubsUsersAdmin.getRole().equals(ClubRole.ADMIN)) {
            throw new ForbiddenException("Только администратор клуба может исключить юзера из состава.");
        }

        ClubsUsers clubsUsersUser = unionService.getClubsUsersOrNotFound(clubId, userId);
        if (clubsUsersUser.getStatus().equals(Status.DELETE)) {
            throw new ConflictException("Данный юзер уже не является членом клуба");
        }

        outOfClub(clubId, userId);
        return true;
    }

    @Override
    @Transactional
    public Boolean exitOutOfClub(Long clubId, Long yourId) {

        unionService.getClubOrNotFound(clubId);
        unionService.getUserOrNotFound(yourId);

        ClubsUsers clubsUsers = unionService.getClubsUsersOrNotFound(clubId, yourId);
        if (clubsUsers.getStatus().equals(Status.DELETE)) {
            throw new ConflictException("Ваше членство в клубе было отозвано ранее.");
        }

        outOfClub(clubId, yourId);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserShortDto> getAllSubscribersClubById(Integer from, Integer size, Long clubId) {

        unionService.getClubOrNotFound(clubId);

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<User> userList = clubRepository.getAllUsersClubById(clubId, pageRequest);

        return userList.stream().map(user -> UserMapper.returnUserShortDto(user)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClubDto getPrivateClubById(Long clubId, Long yourId) {

        Club club = unionService.getClubOrNotFound(clubId);
        User user = unionService.getUserOrNotFound(yourId);
        ClubsUsers clubsUsers = unionService.getClubsUsersOrNotFound(clubId, yourId);

        if (clubsUsers.getStatus().equals(Status.DELETE)) {
            throw new ConflictException(String.format("User  id %d, на данный момент не является членом клуба.", yourId));
        }
        if (clubsUsers.getRole().equals(ClubRole.USER)) {
            throw new ConflictException(String.format("User id %d, не руководитель группы.", yourId));
        }

        ClubDto clubDto = ClubMapper.returnClubDto(club);
        clubDto.setSubscribersCount(clubsUsersRepository.subscribersCount(clubId));

        return clubDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ClubShortDto getPublicClubById(Long clubId) {

        Club club = unionService.getClubOrNotFound(clubId);
        ClubShortDto clubShortDto = ClubMapper.returnClubShortDto(club);
        clubShortDto.setSubscribersCount(clubsUsersRepository.subscribersCount(clubId));

        return clubShortDto;
    }

    @Override
    @Transactional
    public Boolean deletePrivateClub(Long yourId, Long clubId) {

        ClubsUsers clubsUsers = unionService.getClubsUsersOrNotFound(clubId, yourId);

        if (!clubsUsers.getRole().equals(ClubRole.ADMIN)) {
            throw new ConflictException(String.format("User %d не является админом клуба club %d", yourId, clubId));
        }

        clubRepository.deleteById(clubId);

        return true;
    }

    @Override
    @Transactional
    public Boolean deleteClub(Long clubId) {

        unionService.getClubOrNotFound(clubId);
        clubRepository.deleteById(clubId);

        return true;
    }

    @Override
    @Transactional
    public ClubDto updateClubById(Long yourId, Long clubId, ClubUpdateDto clubUpdateDto) {

        ClubsUsers clubsUsers = unionService.getClubsUsersOrNotFound(clubId, yourId);

        if (!clubsUsers.getRole().equals(ClubRole.ADMIN)) {
            throw new ConflictException(String.format("User %d не является админом клуба club %d", yourId, clubId));
        }

        Club club = clubRepository.findById(clubsUsers.getClubId()).get();

        if (clubUpdateDto.getName() != null && !clubUpdateDto.getName().isBlank()) {
            club.setName(clubUpdateDto.getName());
        }
        if (clubUpdateDto.getDescriptions() != null && !clubUpdateDto.getDescriptions().isBlank()) {
            club.setDescriptions(clubUpdateDto.getDescriptions());
        }
        if (clubUpdateDto.getPhoto() != null && !clubUpdateDto.getPhoto().isBlank()) {
            club.setPhoto(clubUpdateDto.getPhoto());
        }
        if (clubUpdateDto.getCreated() != null && !clubUpdateDto.getCreated().isBefore(CURRENT_TIME)) {
            club.setCreated(clubUpdateDto.getCreated());
        }

        club = clubRepository.save(club);

        return ClubMapper.returnClubDto(club);
    }

    @Override
    public List<ClubDto> getAllClubsByAdminFromParam(Integer from, Integer size, String type, Long breedId) {
        PetType petType = unionService.makePetType(type);

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Club> clubList = clubRepository.findClubByParam(petType, breedId, pageRequest);

        return  clubList.stream().map(ClubMapper::returnClubDto).collect(Collectors.toList());

    }

    @Override
    public List<ClubShortDto> getAllClubByPublicFromParam(Integer from, Integer size, String type, Long breedId) {

        PetType petType = unionService.makePetType(type);

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Club> clubList = clubRepository.findClubByParam(petType, breedId, pageRequest);

        return  clubList.stream().map(ClubMapper::returnClubShortDto).collect(Collectors.toList());
    }

    private void outOfClub (Long clubId, Long userId) {

        ClubsUsers clubsUsers = unionService.getClubsUsersOrNotFound(clubId, userId);
        clubsUsers.setStatus(Status.DELETE);
        clubsUsers.setCreated(LocalDate.from(CURRENT_TIME));

        clubsUsersRepository.save(clubsUsers);
    }
}
