package pl.zablocki.petvet.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.Authority;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.dto.UserDto;
import pl.zablocki.petvet.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityService authorityService) {
        this.userRepository = userRepository;
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

        user.setPassword(userDto.getPassword());
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

}



