package pl.zablocki.petvet.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.PetType;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.repository.PetTypeRepository;
import pl.zablocki.petvet.services.PetService;
import pl.zablocki.petvet.services.UserService;

@Configuration
public class RepositoriesInitializer {


    private final PetTypeRepository petTypeRepository;
    private final PetService petService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RepositoriesInitializer(PetTypeRepository petTypeRepository, PetService petService, UserService userService, PasswordEncoder passwordEncoder) {
        this.petTypeRepository = petTypeRepository;
        this.petService = petService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    InitializingBean init() {

        return () -> {

            if(!userService.getUserByEmail("zablo432432@o2.pl").isPresent()){
                userService.createUserAccount("zablo432432@o2.pl", "123");
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

                User zablo = userService.getUserByEmail("zablo432432@o2.pl").get();
                PetType dog = petTypeRepository.findOneByName("Dog").get();
                PetType cat = petTypeRepository.findOneByName("Cat").get();
                Pet fiflak = new Pet();
                fiflak.setName("Fifi");
                fiflak.setOwner(zablo);
                fiflak.setAge(12);
                fiflak.setSex("Female");
                fiflak.setType(dog);
                fiflak.setBreed("Kundel");

                Pet kitler = new Pet();
                kitler.setName("Kitler");
                kitler.setOwner(zablo);
                kitler.setAge(666);
                kitler.setSex("Male");
                kitler.setType(cat);
                kitler.setBreed("Szatan");

                petService.savePet(fiflak);
                petService.savePet(kitler);
            }
        };
    }
}