package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zablocki.petvet.entity.Pet;
import pl.zablocki.petvet.entity.User;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByOwner(User owner);
}
