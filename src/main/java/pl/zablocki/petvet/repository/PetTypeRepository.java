package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zablocki.petvet.entity.PetType;

import java.util.Optional;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {

    <S extends PetType> Optional<S> findOneByName(String name);
}
