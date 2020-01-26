package pl.zablocki.petvet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.zablocki.petvet.entity.Examination;
import pl.zablocki.petvet.entity.Owner;

import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    Page<Examination> findAllByOwner(Owner owner, Pageable pageable);
}
