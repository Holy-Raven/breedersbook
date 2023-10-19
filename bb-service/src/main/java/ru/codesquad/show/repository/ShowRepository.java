package ru.codesquad.show.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.show.model.Show;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    Page<Show> findShowsByPetId(long petId, Pageable page);

    List<Show> findShowsByPetIdIn(List<Long> petIds); //служебный метод для родословной
}
