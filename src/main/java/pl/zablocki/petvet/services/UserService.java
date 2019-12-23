package pl.zablocki.petvet.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zablocki.petvet.entity.User;
import pl.zablocki.petvet.entity.dto.UserDto;
import pl.zablocki.petvet.repository.UserRepository;

@Service
public class UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addNewUserFromDto(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public void updateUser(UserDto userDto) {
        User user = getUserByEmail(userDto.getEmail());

        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }

}



