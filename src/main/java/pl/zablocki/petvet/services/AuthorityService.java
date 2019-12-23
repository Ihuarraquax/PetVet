package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.Authority;
import pl.zablocki.petvet.repository.AuthorityRepository;

@Service
public class AuthorityService {

    AuthorityRepository repository;

    public AuthorityService(AuthorityRepository repository) {
        this.repository = repository;
    }

    public void addAuthority(Authority authority) {
        repository.saveAndFlush(authority);
    }
}
