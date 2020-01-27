package pl.zablocki.petvet.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zablocki.petvet.entity.*;
import pl.zablocki.petvet.entity.appointments.Appointment;
import pl.zablocki.petvet.model.Credentials;
import pl.zablocki.petvet.repository.AppointmentRepository;
import pl.zablocki.petvet.repository.ExaminationRepository;
import pl.zablocki.petvet.repository.PetTypeRepository;
import pl.zablocki.petvet.services.AccountService;
import pl.zablocki.petvet.services.PetService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class RepositoriesInitializer {


    private final PetTypeRepository petTypeRepository;
    private final PetService petService;
    private final AccountService accountService;
    private final AppointmentRepository appointmentRepository;
    private final ExaminationRepository examinationRepository;
    private final PasswordEncoder passwordEncoder;
    Pet fiflak;
    Pet kitler;
    Owner zablo;
    Vet vet;
    private Appointment appointment2;
    private Appointment appointment;

    public RepositoriesInitializer(PetTypeRepository petTypeRepository, PetService petService, AccountService accountService, AppointmentRepository appointmentRepository, ExaminationRepository examinationRepository, PasswordEncoder passwordEncoder) {
        this.petTypeRepository = petTypeRepository;
        this.petService = petService;
        this.accountService = accountService;
        this.appointmentRepository = appointmentRepository;
        this.examinationRepository = examinationRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    InitializingBean init() {
        return () -> {

            if(!accountService.getOwnerByEmail("zablo432432@o2.pl").isPresent()){
                accountService.createOwnerAccount("zablo432432@o2.pl", "123");
                accountService.enableAccount("zablo432432@o2.pl");
                zablo = accountService.getOwnerByEmail("zablo432432@o2.pl").get();
                accountService.setCredentials("zablo432432@o2.pl", new Credentials("Hubert", "Zabłocki", LocalDate.of(1997, 9, 17), "123-725-473"));
            }
            if(!accountService.getVetByEmail("vet@o2.pl").isPresent()){
                accountService.createVetAccount("vet@o2.pl", "123");
                accountService.setCredentials("vet@o2.pl", new Credentials("Jan", "Nowak", LocalDate.now().minusYears(53),"142-324-534"));
                accountService.enableAccount("vet@o2.pl");
                vet = accountService.getVetByEmail("vet@o2.pl").get();
            }

            if (petTypeRepository.findAll().isEmpty()) {
                PetType dog = new PetType();
                dog.setName("Dog");
                PetType cat = new PetType();
                cat.setName("Cat");
                PetType parrot = new PetType();
                parrot.setName("Parrot");
                PetType rabbit = new PetType();
                rabbit.setName("Rabbit");
                PetType guineaPig = new PetType();
                guineaPig.setName("Guinea Pig");

                petTypeRepository.save(dog);
                petTypeRepository.save(cat);
                petTypeRepository.save(parrot);
                petTypeRepository.save(rabbit);
                petTypeRepository.save(guineaPig);
            }

            if (petService.findAll().isEmpty()){

                PetType dog = petTypeRepository.findOneByName("Dog").get();
                PetType cat = petTypeRepository.findOneByName("Cat").get();
                fiflak = new Pet();
                fiflak.setName("Fifi");
                fiflak.setOwner(zablo);
                fiflak.setAge(12);
                fiflak.setSex("Female");
                fiflak.setType(dog);
                fiflak.setBreed("Kundel");

                kitler = new Pet();
                kitler.setName("Kitler");
                kitler.setOwner(zablo);
                kitler.setAge(666);
                kitler.setSex("Male");
                kitler.setType(cat);
                kitler.setBreed("Szatan");

                fiflak = petService.savePet(fiflak);
                kitler = petService.savePet(kitler);
            }

            if(appointmentRepository.findAll().isEmpty()){
                appointment = new Appointment();
                appointment.setOwner(zablo);
                appointment.setApproved(false);
                appointment.setStartDate(LocalDateTime.now().withHour(8).withMinute(0).withSecond(0));
                appointment.setEndDate(LocalDateTime.now().withHour(9).withMinute(0).withSecond(0));
                appointment.setPet(fiflak);
                appointment.setVet(vet);
                appointment.setSymptomsDescription("umiera");
                appointmentRepository.save(appointment);

                appointment2 = new Appointment();
                appointment2.setOwner(zablo);
                appointment2.setApproved(false);
                appointment2.setStartDate(LocalDateTime.now().plusDays(1).withHour(14).withMinute(0).withSecond(0));
                appointment2.setEndDate(LocalDateTime.now().withHour(15).withMinute(0).withSecond(0));
                appointment2.setPet(kitler);
                appointment2.setVet(vet);
                appointment2.setSymptomsDescription("zdycha");

                appointmentRepository.save(appointment2);
            }

            if(examinationRepository.findAll().isEmpty()){
                Examination examination = new Examination();
                examination.setOwner(zablo);
                examination.setPet(fiflak);
                examination.setVet(vet);
                examination.setDate(appointment.getStartDate());
                examination.setDiagnosis("diagn");
                examination.setPrescription("presc");
                examination.setResults("result");
                examination.setExaminationType(ExaminationType.Surgery);

                Examination examination2 = new Examination();
                examination2.setOwner(zablo);
                examination2.setPet(kitler);
                examination2.setVet(vet);
                examination2.setDate(appointment2.getStartDate());
                examination2.setDiagnosis("diag2");
                examination2.setPrescription("presc2");
                examination2.setResults("result2");
                examination2.setExaminationType(ExaminationType.Preventive);

                examinationRepository.save(examination);
                examinationRepository.save(examination2);
            }
        };
    }
}