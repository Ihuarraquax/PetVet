package pl.zablocki.petvet.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zablocki.petvet.entity.PetType;
import pl.zablocki.petvet.repository.PetTypeRepository;

@Configuration
public class RepositoriesInitializer {


    private final PetTypeRepository petTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public RepositoriesInitializer(PetTypeRepository petTypeRepository, PasswordEncoder passwordEncoder) {
        this.petTypeRepository = petTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    InitializingBean init() {

        return () -> {
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
        };
    }
}