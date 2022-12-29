package common.commonbackend.user;

import common.commonbackend.entities.User;
import common.commonbackend.house.HouseEntity;
import common.commonbackend.house.HouseService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Log4j2
@MockitoSettings
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HouseService houseService;

    private UserService userService;
    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, houseService);
    }

    @Test
    void shouldCreateUser() {
        // given
        String username = "username";
        String password = "password";
        String joinCode = "joinCode";

        String encodedPassword = UserService.getPasswordEncoder().encode(password);
        when(userRepository.save(any())).thenAnswer(returnsFirstArg());

        HouseEntity house = new HouseEntity();
        house.setJoinCode(joinCode);
        when(houseService.getOrCreateHouse(joinCode)).thenReturn(house);
        User expectedUser = new User(username, encodedPassword, house);

        // when
        User actual = userService.createUser(username, password, joinCode);

        // then
        assertThat(actual.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(actual.getHouse().getJoinCode()).isEqualTo(expectedUser.getHouse().getJoinCode());
        assertThat(UserService.getPasswordEncoder().matches(password, actual.getPassword())).isTrue();
    }

    @Test
    void shouldLoadUserByUsernameWithExistingUser() {
        // given
        String username = "username";
        String password = "password";
        String joinCode = "joinCode";
        HouseEntity house = new HouseEntity();
        house.setJoinCode(joinCode);
        User user = new User(username, password, house);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // when
        UserDetails actual = userService.loadUserByUsername(username);

        // then
        verify(userRepository, times(1)).findByUsername(username);
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getPassword()).isEqualTo(password);
    }

    @Test
    void shouldLoadUserByUsernameWithNoUser() {
        // given
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(null);

        //when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> userService.loadUserByUsername(username);

        //then
        assertThatThrownBy(throwingCallable).isInstanceOf(UsernameNotFoundException.class);
    }

}