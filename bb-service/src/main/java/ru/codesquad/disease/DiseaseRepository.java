package ru.codesquad.disease;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codesquad.disease.model.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
