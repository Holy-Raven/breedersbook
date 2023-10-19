package ru.codesquad.show.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.show.model.Show;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findShowsByPetId(long petId);
    List<Show> findShowsByPetIdIn(List<Long> petIds);
}
