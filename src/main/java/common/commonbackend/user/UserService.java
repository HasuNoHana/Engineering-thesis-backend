package common.commonbackend.user;


import common.commonbackend.entities.User;
import common.commonbackend.entities.UserPrincipal;
import common.commonbackend.house.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    private final HouseService houseService;

    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    public User createUser(String username, String password, String joinCode) {
        //TODO validate username and password
        String encodedPassword = PASSWORD_ENCODER.encode(password);
        User user = new User(username, encodedPassword, houseService.getOrCreateHouse(joinCode));
        return userRepository.save(user);
    }

    public static PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if(user==null) {
            throw new UsernameNotFoundException("User not found!");
        }
        //so what do we return? So we would create a class that implements UserDetails

        return new UserPrincipal(user);

    }
}
