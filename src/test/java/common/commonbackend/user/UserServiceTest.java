package common.commonbackend.user;

import common.commonbackend.entities.User;
import common.commonbackend.house.HouseEntity;
import common.commonbackend.house.HouseService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log4j2
@MockitoSettings
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HouseService houseService;

    @Test
    void shouldCreateUser() {
        // given
        String username = "username";
        String password = "password";
        String joinCode = "joinCode";
        UserService userService = new UserService(userRepository, houseService);
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

}