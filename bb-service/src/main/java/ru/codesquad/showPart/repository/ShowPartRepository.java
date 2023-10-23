package ru.codesquad.showPart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.showPart.model.ShowPart;

import java.util.List;

@Repository
public interface ShowPartRepository extends JpaRepository<ShowPart, Long> {
    Page<ShowPart> findShowsByPetId(long petId, Pageable page);

    List<ShowPart> findShowsByPetIdIn(List<Long> petIds); //служебный метод для родословной
}
