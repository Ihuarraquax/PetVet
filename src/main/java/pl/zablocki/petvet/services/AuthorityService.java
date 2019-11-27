package pl.zablocki.petvet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.Authority;
import pl.zablocki.petvet.repository.AuthorityRepository;

@Service
public class AuthorityService {

    @Autowired
    AuthorityRepository repository;

    public void addAuthority(Authority authority) {
        repository.saveAndFlush(authority);
    }
}
