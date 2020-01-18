package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zablocki.petvet.entity.Examination;
@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
}
