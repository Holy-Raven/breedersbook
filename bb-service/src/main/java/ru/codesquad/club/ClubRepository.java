package ru.codesquad.club;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.codesquad.user.User;
import ru.codesquad.util.enums.PetType;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query(value = "SELECT u FROM User AS u WHERE u.id IN " +
            "(SELECT cu.userId FROM ClubsUsers AS cu WHERE cu.clubId = :clubId AND cu.status != 'DELETE')" +
            "ORDER BY u.id")
    List<User> getAllUsersClubById(@Param("clubId") Long clubId,
                                   PageRequest pageRequest);

    @Query(value = "SELECT c FROM Club AS c " +
            "WHERE (:type IS NULL OR c.type = :type) AND (:breed IS NULL OR :breed = c.breed.id) " +
            "GROUP BY c.id " +
            "ORDER BY c.id ASC")
    List<Club> findClubByParam(@Param("type") PetType type,
                               @Param("breed") Long breed,
                               PageRequest pageRequest);
}
