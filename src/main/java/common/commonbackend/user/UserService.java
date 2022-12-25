package common.commonbackend.user;


import common.commonbackend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    public User createUser(String username, String password) {
        //TODO validate username and password
        String encodedPassword = PASSWORD_ENCODER.encode(password);
        User user = new User(username, encodedPassword, null); //TODO add house enitty
        return userRepository.save(user);
    }

    public static PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }
}
