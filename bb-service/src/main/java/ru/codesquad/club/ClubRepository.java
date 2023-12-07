package ru.codesquad.club;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.codesquad.user.User;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query(value = "SELECT u FROM User AS u WHERE u.id IN " +
    "(SELECT cu.userId FROM ClubsUsers AS cu WHERE cu.clubId = :clubId AND cu.status != 'DELETE')" +
    "ORDER BY u.id")
    List<User> getAllUsersClubById(@Param("clubId") Long clubId, PageRequest pageRequest);
}
