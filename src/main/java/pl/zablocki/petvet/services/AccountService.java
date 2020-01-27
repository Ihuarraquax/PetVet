package pl.zablocki.petvet.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.Authority;
import pl.zablocki.petvet.entity.Owner;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.Vet;
import pl.zablocki.petvet.entity.dto.UserDto;
import pl.zablocki.petvet.model.Credentials;
import pl.zablocki.petvet.model.Speciality;
import pl.zablocki.petvet.repository.CredentialsRepository;
import pl.zablocki.petvet.repository.OwnerRepository;
import pl.zablocki.petvet.repository.UserRepository;
import pl.zablocki.petvet.repository.VetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public AccountService(UserRepository userRepository, OwnerRepository ownerRepository, VetRepository vetRepository, CredentialsRepository credentialsRepository, PasswordEncoder passwordEncoder, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.vetRepository = vetRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public void updateUser(UserDto userDto) {
        User user = getUserByEmail(userDto.getEmail()).get();

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public void createUserAccount(String email, String password) {
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setEmail(email);
        authorityService.addAuthority(authority);

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        userRepository.save(user);
    }

    public void createVetAccount(String email, String password) {
        Authority authority = new Authority();
        authority.setAuthority("ROLE_VET");
        authority.setEmail(email);
        authorityService.addAuthority(authority);

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        User savedUser = userRepository.save(user);

        Vet vet = new Vet(savedUser, null, Speciality.Fish);
        vetRepository.save(vet);

    }

    public Optional<Vet> getVetByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return vetRepository.findByUser(user.get());
        }

        return Optional.empty();
    }

    public void setCredentials(String email, Credentials credentials) {
        Optional<Vet> vetOpt = getVetByEmail(email);
        Optional<Owner> ownerOpt = getOwnerByEmail(email);
        if (vetOpt.isPresent()) {
            Credentials savedCredentials = credentialsRepository.save(credentials);
            Vet vet = vetOpt.get();
            vet.setCredentials(savedCredentials);
            vetRepository.saveAndFlush(vet);
        }
        if (ownerOpt.isPresent()) {
            Credentials savedCredentials = credentialsRepository.save(credentials);
            Owner owner = ownerOpt.get();
            owner.setCredentials(savedCredentials);
            ownerRepository.saveAndFlush(owner);
        }
    }

    public Optional<Owner> getOwnerByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return ownerRepository.findByUser(user.get());
        }

        return Optional.empty();
    }

    public Owner createOwnerAccount(String email, String password) {
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setEmail(email);
        authorityService.addAuthority(authority);

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        User savedUser = userRepository.save(user);

        Owner owner = new Owner(savedUser, null);
        return ownerRepository.save(owner);

    }

    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    public UserDto getActiveUserDto(String email) {
        User user = null;

        if(isOwner(email)){
            Owner owner = getOwnerByEmail(email).get();
            user = owner.getUser();
        } else if(isVet(email)){
            Vet vet = getVetByEmail(email).get();
            user = vet.getUser();
        }
        return convertToDto(user);
    }


    public boolean isOwner(String email) {
        Optional<Owner> ownerByEmail = getOwnerByEmail(email);
        return ownerByEmail.isPresent();
    }

    public boolean isVet(String email) {
        Optional<Vet> vetByEmail = getVetByEmail(email);
        return vetByEmail.isPresent();
    }

    public void enableAccount(String email) {
        User user = getUserByEmail(email).get();
        user.setEnabled(true);
        userRepository.save(user);
    }
}



