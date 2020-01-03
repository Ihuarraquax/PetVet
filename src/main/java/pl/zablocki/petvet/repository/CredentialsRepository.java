package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zablocki.petvet.model.Credentials;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials,Long> {
}
