package ru.codesquad.club;

import ru.codesquad.club.clubsusers.dto.ClubsUsersShortDto;
import ru.codesquad.club.dto.ClubDto;
import ru.codesquad.club.dto.ClubNewDto;
import ru.codesquad.club.dto.ClubShortDto;
import ru.codesquad.club.dto.ClubUpdateDto;
import ru.codesquad.user.dto.UserShortDto;

import java.util.List;

public interface ClubService {

    ClubDto addClub(Long yourId, ClubNewDto ClubNewDto);

    ClubsUsersShortDto joinInClub(Long yourId, Long clubId);

    Boolean kickOutOfClub(Long clubId, Long yourId, Long userId);

    Boolean exitOutOfClub(Long clubId, Long yourId);

    List<UserShortDto> getAllSubscribersClubById(Integer from, Integer size, Long clubId);

    ClubDto getPrivateClubById(Long clubId, Long yourId);

    ClubShortDto getPublicClubById(Long clubId);

    Boolean deletePrivateClub(Long yourId, Long clubId);

    Boolean deleteClub(Long clubId);

    ClubDto updateClubById(Long yourId, Long clubId, ClubUpdateDto clubUpdateDto);

    List<ClubShortDto> getAllClubByPublicFromParam(Integer from, Integer size, String type, Long breedId);

    List<ClubDto>  getAllClubsByAdminFromParam(Integer from, Integer size, String type, Long breedId);
}
