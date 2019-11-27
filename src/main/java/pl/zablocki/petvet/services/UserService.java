package pl.zablocki.petvet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.UserDto;
import pl.zablocki.petvet.repository.UserRepository;
import pl.zablocki.petvet.validation.email.EmailExistsException;

@Service
public class UserService {

    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addNewUserFromDto(UserDto userDto) throws EmailExistsException {

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }

}



