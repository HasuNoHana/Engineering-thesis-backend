package common.commonbackend.user;


import common.commonbackend.house.HouseEntity;
import common.commonbackend.house.HouseService;
import common.commonbackend.house.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.security.UserPrincipal;
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
    public static final long DEFAULT_POINTS = 0L;
    public static final long DEFAULT_RANGE = 100L;
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg";
    private final UserRepository userRepository;
    private final HouseService houseService;
    private final UserInformationRepository userInformationRepository;

    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    public User createUser(String username, String password, String joinCode) {
        if (username == null || password == null || joinCode == null
        || username.equals("") || password.equals("") || joinCode.equals("")) {
            throw new IllegalArgumentException("Username, password or join code cannot be empty");
        }

        HouseEntity house = houseService.getHouseForJoinCode(joinCode);
        if(house == null) {
            throw new WrongHouseJoinCodeException("House for join code: " + joinCode + " not found");
        }

        UserInformation userInformation = this.createUserInformationForUser(house);
        User user = new User(
                username,
                PASSWORD_ENCODER.encode(password),
                userInformation);
        return userRepository.save(user);
    }

    public User createUser(String username, String password) {
        if(username == null || password == null || username.equals("") || password.equals("")) {
            throw new IllegalArgumentException("Username or password code cannot be empty");
        }

        UserInformation userInformation = this.createUserInformationForUser(houseService.createNewHouse());
        User user = new User(
                username,
                PASSWORD_ENCODER.encode(password),
                userInformation);
        return userRepository.save(user);
    }

    public static PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        //so what do we return? So we would create a class that implements UserDetails

        return new UserPrincipal(user);

    }

    public UserInformation createUserInformationForUser(HouseEntity house) {
        UserInformation userInformation = new UserInformation(DEFAULT_POINTS, DEFAULT_RANGE, DEFAULT_IMAGE, house);
        return userInformationRepository.save(userInformation);
    }
}
