package ru.codesquad.kennel;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.codesquad.util.enums.PetType;

import java.util.List;

public interface KennelRepository extends JpaRepository<Kennel, Long> {

    @Query(value = "SELECT k FROM Kennel AS k " +
            "WHERE (:type IS NULL OR k.type = :type) AND (:breed IS NULL OR :breed = k.breed.id) " +
            "GROUP BY k.id " +
            "ORDER BY k.id ASC")
    List<Kennel> findKennelByParam(@Param("type") PetType type,
                                   @Param("breed") Long breedId,
                                   PageRequest pageRequest);
}
