package ru.codesquad.vac;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacRepository extends JpaRepository<Vac, Long> {
}
