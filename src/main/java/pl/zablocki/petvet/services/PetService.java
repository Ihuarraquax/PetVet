package pl.zablocki.petvet.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import pl.zablocki.petvet.controllers.commands.PetSpecification;
import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.PetType;
import pl.zablocki.petvet.repository.PetRepository;
import pl.zablocki.petvet.repository.PetTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetTypeRepository petTypeRepository;
    private final AccountService accountService;
    private final PetRepository petRepository;

    public PetService(PetTypeRepository petTypeRepository, AccountService accountService, PetRepository petRepository) {
        this.petTypeRepository = petTypeRepository;
        this.accountService = accountService;
        this.petRepository = petRepository;
    }

    public List<PetType> getPetTypes() {
        return petTypeRepository.findAll();
    }

    public Pet savePet(Pet pet) {
        return petRepository.saveAndFlush(pet);
    }

    public void savePet(Pet pet, String email) {
        if (pet.getOwner() == null) {
            Owner owner = accountService.getOwnerByEmail(email).get();
            pet.setOwner(owner);
        }
        petRepository.saveAndFlush(pet);
    }

    public Page<Pet> getOwnerPets(String email, Pageable pageable, PetSpecification spec) {
        Owner owner = accountService.getOwnerByEmail(email).get();
        Page<Pet> all = petRepository.findAll(spec, pageable);
        return new PageImpl<>(all.stream().filter(pet -> pet.getOwner().getId() == owner.getId()).collect(Collectors.toList()));
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Optional<Pet> getPet(long id) {
        return petRepository.findById(id);
    }

    public Page<Pet> getAllPets(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    public Page<Pet> getAllPets(Pageable pageable, PetSpecification spec) {
        return petRepository.findAll(spec, pageable);
    }

    public void deletePet(Pet pet) {
        pet.setOwner(null);
        petRepository.save(pet);
    }
}
