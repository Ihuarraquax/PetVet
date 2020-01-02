package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.PetType;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.repository.PetRepository;
import pl.zablocki.petvet.repository.PetTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetTypeRepository petTypeRepository;
    private final UserService userService;
    private final PetRepository petRepository;

    public PetService(PetTypeRepository petTypeRepository, UserService userService, PetRepository petRepository) {
        this.petTypeRepository = petTypeRepository;
        this.userService = userService;
        this.petRepository = petRepository;
    }

    public List<PetType> getPetTypes() {
        return petTypeRepository.findAll();
    }

    public void savePet(Pet pet) {
        petRepository.saveAndFlush(pet);
    }

    public void savePet(Pet pet, String email) {
        if (pet.getOwner() == null) {
            User owner = userService.getUserByEmail(email).get();
            pet.setOwner(owner);
        }
        petRepository.saveAndFlush(pet);
    }

    public List<Pet> getUserPets(String email) {
        User owner = userService.getUserByEmail(email).get();
        return petRepository.findAllByOwner(owner);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Optional<Pet> getPet(long id) {
        return petRepository.findById(id);
    }
}
