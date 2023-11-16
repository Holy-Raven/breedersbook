package ru.codesquad.club.clubsusers;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.codesquad.club.Club;
import ru.codesquad.user.User;

import java.util.List;

public interface ClubsUsersRepository extends JpaRepository<ClubsUsers, ClubsUsersId> {

//    List<ClubsUsers> findAllByClubsUsersId_Club(Club club);
//
//    List<ClubsUsers> findAllByClubsUsersId_User(User user);

    List<ClubsUsers> findByClub(Club club);

    List<ClubsUsers> findByUser(User user);
}
