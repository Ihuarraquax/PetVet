package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.PetType;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.repository.PetRepository;
import pl.zablocki.petvet.repository.PetTypeRepository;

import java.util.List;

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

    public void addPet(Pet pet) {
        petRepository.saveAndFlush(pet);
    }

    public void addPet(Pet pet, String email) {
        User owner = userService.getUserByEmail(email);
        pet.setOwner(owner);
        petRepository.saveAndFlush(pet);
    }

    public List<Pet> getUserPets(String email) {
        User owner = userService.getUserByEmail(email);
        return petRepository.findAllByOwner(owner);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }
}
