package ru.codesquad.club;

import ru.codesquad.club.dto.ClubDto;
import ru.codesquad.club.dto.ClubNewDto;

public interface ClubService {
    ClubDto addClub(Long yourId, ClubNewDto ClubNewDto);
}
