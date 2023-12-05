package ru.codesquad.club.clubsusers.dto;

import ru.codesquad.club.clubsusers.ClubsUsers;

public class ClubsUsersMapper {

    public static ClubsUsersShortDto returnClubsUsersShortDto (ClubsUsers clubsUsers) {
        ClubsUsersShortDto clubsUsersDto = ClubsUsersShortDto.builder()
                .clubId(clubsUsers.getClubId())
                .userId(clubsUsers.getUserId())
                .role(clubsUsers.getRole().toString())
                .build();
        return clubsUsersDto;
    }

    public static ClubsUsersFullDto returnClubsUsersFullDto (ClubsUsers clubsUsers) {
        ClubsUsersFullDto clubsUsersDto = ClubsUsersFullDto.builder()
                .clubId(clubsUsers.getClubId())
                .userId(clubsUsers.getUserId())
                .role(clubsUsers.getRole().toString())
                .status(clubsUsers.getStatus().toString())
                .created(clubsUsers.getCreated())
                .build();
        return clubsUsersDto;
    }
}
