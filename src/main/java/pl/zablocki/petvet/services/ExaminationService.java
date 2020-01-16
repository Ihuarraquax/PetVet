package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.Examination;
import pl.zablocki.petvet.repository.ExaminationRepository;

@Service
public class ExaminationService {

    private final ExaminationRepository examinationRepository;

    public ExaminationService(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }


    public void saveExamination(Examination examination) {
        examinationRepository.save(examination);
    }
}
