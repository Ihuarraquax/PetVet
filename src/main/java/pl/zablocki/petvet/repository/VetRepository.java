package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.Vet;

import java.util.Optional;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    Optional<Vet> findByUser(User user);
}
