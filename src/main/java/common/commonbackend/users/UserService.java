package common.commonbackend.users;


import common.commonbackend.houses.HouseEntity;
import common.commonbackend.houses.HouseService;
import common.commonbackend.houses.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.security.UserPrincipal;
import common.commonbackend.tasks.Task;
import common.commonbackend.tasks.TaskService;
import common.commonbackend.users.house_buddy.HouseBuddy;
import common.commonbackend.users.house_buddy.HouseBuddyRepository;
import common.commonbackend.users.house_buddy.HouseBuddyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final HouseService houseService;
    private final HouseBuddyService houseBuddyService;
    private final HouseBuddyRepository houseBuddyRepository;
    private final TaskService taskService;

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        return user.get();
    }

    public User createUser(String username, String password, String joinCode) {
        if (isAnyCredentialEmpty(username, password, joinCode)) {
            throw new IllegalArgumentException("Username, password or join code cannot be empty");
        }

        HouseEntity house = houseService.getHouseForJoinCode(joinCode);
        if (house == null) {
            throw new WrongHouseJoinCodeException("House for join code: " + joinCode + " not found");
        }

        User user = new User(
                username,
                PASSWORD_ENCODER.encode(password),
                houseBuddyService.getDefaultHouseBuddy(house));
        return userRepository.save(user);
    }

    private static boolean isAnyCredentialEmpty(String... credentials) {
        for (String credential : credentials) {
            if (credential == null || "".equals(credential)) {
                return true;
            }
        }
        return false;
    }

    public User createUser(String username, String password) {
        if (isAnyCredentialEmpty(username, password)) {
            throw new IllegalArgumentException("Username or password code cannot be empty");
        }

        User user = new User(
                username,
                PASSWORD_ENCODER.encode(password),
                houseBuddyService.getDefaultHouseBuddy(houseService.createNewHouse()));
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
        return new UserPrincipal(user);
    }

    List<User> getUsersForHouse(HouseEntity myHouse) {
        return this.userRepository.findByHouseBuddy_House(myHouse);
    }

    User editUser(Long id, UserDTO newUser) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        HouseBuddy houseBuddy = user.get().getHouseBuddy();
        houseBuddy.setWeeklyFirewoodContribution(newUser.getRange());
        houseBuddy.setAvatarImageUrl(newUser.getImage());
        houseBuddyRepository.save(houseBuddy);
        return user.get();
    }

    long countDoneTasksThisWeek(Long userId, HouseEntity house) {
        List<Task> tasksForCurrentHouse = taskService.getTasks(house);
        return tasksForCurrentHouse.stream().filter(task -> (task.getLastDoneUserId() == userId) &&
                (ChronoUnit.DAYS.between(task.getLastDoneDate(), LocalDate.now()) <= 7) &&
                task.isDone()).count();
    }
}
