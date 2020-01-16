package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zablocki.petvet.entity.Examination;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {
}
