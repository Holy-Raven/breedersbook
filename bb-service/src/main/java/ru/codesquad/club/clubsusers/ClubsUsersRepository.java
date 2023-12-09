package ru.codesquad.club.clubsusers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubsUsersRepository extends JpaRepository<ClubsUsers, ClubsUsersId> {
    @Query(value = "SELECT count(*) FROM ClubsUsers AS cu WHERE cu.clubId = :clubId AND cu.status != 'DELETE'")
    Integer subscribersCount(@Param("clubId") Long clubId);
}
