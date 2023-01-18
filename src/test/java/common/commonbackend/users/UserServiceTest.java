package common.commonbackend.users;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.houses.HouseService;
import common.commonbackend.houses.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.tasks.Task;
import common.commonbackend.tasks.TaskService;
import common.commonbackend.users.house_buddy.HouseBuddy;
import common.commonbackend.users.house_buddy.HouseBuddyRepository;
import common.commonbackend.users.house_buddy.HouseBuddyService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Log4j2
@MockitoSettings
class UserServiceTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = UserService.getPasswordEncoder().encode(PASSWORD);
    private static final String JOIN_CODE = "1234";
    private static final long USER_ID = 1L;
    private static final Long FIREWOOD_STACK_SIZE = 40L;

    private static final long WEEKLY_FIREWOOD_CONTRIBUTION = 100L;
    private static final String IMAGE = "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg";
    private static final String NEW_PASSWORD = "newPassword";
    @Mock
    private UserRepository userRepository;
    @Mock
    private HouseService houseService;
    @Mock
    private HouseBuddyService houseBuddyService;
    @Mock
    private HouseEntity house;
    @Mock
    private User user;
    @Mock
    private HouseBuddy houseBuddy;
    @Mock
    private HouseBuddyRepository houseBuddyRepository;
    @Mock
    private TaskService taskService;
    @Mock
    private Task task1;
    @Mock
    private Task task2;
    @Mock
    private Task task3;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, houseService, houseBuddyService,
                houseBuddyRepository, taskService);
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
        assertEquals(2, actual.size());
        assertThat(actual.get(0)).isEqualTo(user1);
        assertThat(actual.get(1)).isEqualTo(user2);
    }

    @Test
    void shouldEditUser() {
        // given
        UserDTO userDTO = new UserDTO(USER_ID, USERNAME, FIREWOOD_STACK_SIZE, WEEKLY_FIREWOOD_CONTRIBUTION,
                IMAGE);
        HouseBuddy houseBuddy = new HouseBuddy(FIREWOOD_STACK_SIZE, WEEKLY_FIREWOOD_CONTRIBUTION, IMAGE, house);
        User user = new User(USERNAME, PASSWORD, houseBuddy);
        when(houseBuddyRepository.save(any())).thenAnswer(returnsFirstArg());
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        // when
        User actual = userService.editUser(USER_ID, userDTO);

        // then
        verify(userRepository, times(1)).findById(USER_ID);
        assertThat(actual.getUsername()).isEqualTo(USERNAME);
        assertThat(actual.getHouseBuddy().getFirewoodStackSize()).isEqualTo(FIREWOOD_STACK_SIZE);
        assertThat(actual.getHouseBuddy().getWeeklyFirewoodContribution()).isEqualTo(WEEKLY_FIREWOOD_CONTRIBUTION);
        assertThat(actual.getHouseBuddy().getAvatarImageUrl()).isEqualTo(IMAGE);
    }

    @Test
    void shouldGetUserById() {
        // given
        User user = new User(USERNAME, PASSWORD, houseBuddy);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        // when
        User actual = userService.getUserById(USER_ID);

        // then
        verify(userRepository, times(1)).findById(USER_ID);
        assertThat(actual.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void shouldThrowExceptionWhenNoUser() {
        // given
        User user = new User(USERNAME, PASSWORD, houseBuddy);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        //when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> userService.getUserById(USER_ID);

        //then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldCountDoneTasksThisWeek() {
        //given
        when(task1.getLastDoneUserId()).thenReturn(USER_ID);
        when(task1.getLastDoneDate()).thenReturn(LocalDate.now());
        when(task1.isDone()).thenReturn(true);

        when(task2.getLastDoneUserId()).thenReturn(USER_ID);
        when(task2.getLastDoneDate()).thenReturn(LocalDate.now());
        when(task2.isDone()).thenReturn(false);

        when(task3.getLastDoneUserId()).thenReturn(USER_ID);
        when(task3.getLastDoneDate()).thenReturn(LocalDate.now().minusDays(8));

        List<Task> tasks = List.of(task1, task2, task3);
        when(taskService.getTasks(house)).thenReturn(tasks);

        // when
        long actual = userService.countDoneTasksThisWeek(USER_ID, house);

        // then
        assertThat(actual).isEqualTo(1L);
    }

    @Test
    void shouldChangePasswordForGoodCurrentPassword() {
        //given
        User user = new User(USERNAME, UserService.getPasswordEncoder().encode(PASSWORD), houseBuddy);
        when(userRepository.save(any())).thenAnswer(returnsFirstArg());

        // when
        User actual = userService.changePassword(PASSWORD, NEW_PASSWORD, user);

        // then
        assertTrue(UserService.getPasswordEncoder().matches(NEW_PASSWORD, actual.getPassword()));
    }

    @Test
    void shouldNotChangePasswordForBadCurrentPassword() {
        // given
        User user = new User(USERNAME, UserService.getPasswordEncoder().encode("otherPassword"), houseBuddy);

        //when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> userService.changePassword(PASSWORD, NEW_PASSWORD, user);

        //then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldDeleteUser() {
        // when
        userService.deleteUser(user);

        // then
        verify(userRepository, times(1)).delete(user);
    }

}