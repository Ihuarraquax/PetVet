package pl.zablocki.petvet.repository;

import org.springframework.data.repository.CrudRepository;
import pl.zablocki.petvet.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
