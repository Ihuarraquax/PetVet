package pl.zablocki.petvet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.zablocki.petvet.controllers.commands.PetSpecification;
import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.Pet;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {

    Page<Pet> findAllByOwner(Owner owner, Pageable pageable);

}
