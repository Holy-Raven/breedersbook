package ru.codesquad.surgery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.surgery.model.Surgery;

@Repository
public interface SurgeryRepository extends JpaRepository<Surgery, Long> {
}
