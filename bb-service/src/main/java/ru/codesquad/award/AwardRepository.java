package ru.codesquad.award;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.award.model.Award;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
}
