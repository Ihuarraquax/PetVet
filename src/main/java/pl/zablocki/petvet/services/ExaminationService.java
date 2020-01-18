package pl.zablocki.petvet.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.Examination;
import pl.zablocki.petvet.repository.ExaminationRepository;

import java.util.Optional;

@Service
public class ExaminationService {

    private final ExaminationRepository examinationRepository;

    public ExaminationService(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }


    public void saveExamination(Examination examination) {
        examinationRepository.save(examination);
    }

    public Page<Examination> findAll(Pageable pageable) {
        return examinationRepository.findAll(pageable);
    }

    public Optional<Examination> findExaminationById(long id) {
        return examinationRepository.findById(id);
    }
}
