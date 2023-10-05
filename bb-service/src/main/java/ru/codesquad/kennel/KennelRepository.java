package ru.codesquad.kennel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KennelRepository extends JpaRepository<Kennel, Long> {
}
