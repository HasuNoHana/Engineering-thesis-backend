package common.commonbackend.user;

import common.commonbackend.entities.User;
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

    @Test
    void shouldCreateUser() {
        // given
        String username = "username";
        String password = "password";
        UserService userService = new UserService(userRepository);
        String encodedPassword = UserService.getPasswordEncoder().encode(password);
        when(userRepository.save(any())).thenAnswer(returnsFirstArg());

        User expectedUser = new User(username, encodedPassword, null);

        // when
        User actual = userService.createUser(username, password);

        // then
        assertThat(actual.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(UserService.getPasswordEncoder().matches(password, actual.getPassword())).isTrue();
        assertThat(actual.getHouse()).isNull();
    }

}