package common.commonbackend.users;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.houses.HouseService;
import common.commonbackend.houses.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.users.houseBuddy.HouseBuddy;
import common.commonbackend.users.houseBuddy.HouseBuddyService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Log4j2
@MockitoSettings
class UserServiceTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ENCODED_PASSWORD = UserService.getPasswordEncoder().encode(PASSWORD);
    private static final String JOIN_CODE = "1234";
    @Mock
    private UserRepository userRepository;
    @Mock
    private HouseService houseService;
    @Mock
    private HouseBuddyService houseBuddyService;
    @Mock
    private HouseEntity house;
    @Mock
    private HouseBuddy houseBuddy;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, houseService, houseBuddyService);
    }

    @Test
    void shouldCreateUserForExistingHouse() {
        // given
        when(userRepository.save(any())).thenAnswer(returnsFirstArg());
        when(houseService.getHouseForJoinCode(JOIN_CODE)).thenReturn(house);
        User expectedUser = new User(USERNAME, ENCODED_PASSWORD, houseBuddy);
        when(houseBuddy.getHouse()).thenReturn(house);
        when(houseBuddyService.getDefaultHouseBuddy(house)).thenReturn(houseBuddy);

        // when
        User actual = userService.createUser(USERNAME, PASSWORD, JOIN_CODE);

        // then
        assertThat(actual.getUsername()).isEqualTo(expectedUser.getUsername());
        verify(houseService, times(1)).getHouseForJoinCode(JOIN_CODE);
        assertThat(actual.getHouseBuddy().getHouse()).isEqualTo(expectedUser.getHouseBuddy().getHouse());
        assertThat(UserService.getPasswordEncoder().matches(PASSWORD, actual.getPassword())).isTrue();
    }

    @Test
    void shouldbalabalalWhenHouseNotExist(){
        // given
        when(houseService.getHouseForJoinCode(JOIN_CODE)).thenReturn(null);

        //when
        ThrowableAssert.ThrowingCallable throwingCallable =
                () -> userService.createUser(USERNAME, PASSWORD, JOIN_CODE);

        //then
        assertThatThrownBy(throwingCallable).isInstanceOf(WrongHouseJoinCodeException.class);
    }

    @Test
    void shouldCreateUserAndHouse() {
        // given
        when(userRepository.save(any())).thenAnswer(returnsFirstArg());
        when(houseService.createNewHouse()).thenReturn(house);
        when(houseBuddy.getHouse()).thenReturn(house);
        when(houseBuddyService.getDefaultHouseBuddy(house)).thenReturn(houseBuddy);
        User expectedUser = new User(USERNAME, ENCODED_PASSWORD, houseBuddy);

        // when
        User actual = userService.createUser(USERNAME, PASSWORD);

        // then
        assertThat(actual.getUsername()).isEqualTo(expectedUser.getUsername());
        verify(houseService, times(1)).createNewHouse();
        assertThat(actual.getHouseBuddy().getHouse()).isEqualTo(expectedUser.getHouseBuddy().getHouse());
        assertThat(UserService.getPasswordEncoder().matches(PASSWORD, actual.getPassword())).isTrue();
    }

    @Test
    void shouldLoadUserByUsernameWithExistingUser() {
        // given
        User user = new User(USERNAME, PASSWORD, houseBuddy);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        // when
        UserDetails actual = userService.loadUserByUsername(USERNAME);

        // then
        verify(userRepository, times(1)).findByUsername(USERNAME);
        assertThat(actual.getUsername()).isEqualTo(USERNAME);
        assertThat(actual.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    void shouldLoadUserByUsernameWithNoUser() {
        // given
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        //when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> userService.loadUserByUsername(USERNAME);

        //then
        assertThatThrownBy(throwingCallable).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void shouldGetUsersForHouse() {
        // given
        User user1 = new User("zuza", "haslo", houseBuddy);
        User user2 = new User("filip", "password", houseBuddy);
        List<User> users = List.of(user1,
                user2);
        when(userRepository.findByHouseBuddy_House(house)).thenReturn(users);

        // when
        List<User> actual = userService.getUsersForHouse(house);

        // then
        verify(userRepository, times(1)).findByHouseBuddy_House(house);
        assertEquals(actual.size(), 2);
        assertThat(actual.get(0)).isEqualTo(user1);
        assertThat(actual.get(1)).isEqualTo(user2);
    }

}