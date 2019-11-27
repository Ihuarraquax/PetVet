package pl.zablocki.petvet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zablocki.petvet.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,String> {
}
