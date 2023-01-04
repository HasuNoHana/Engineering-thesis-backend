package common.commonbackend.user;

import common.commonbackend.ControllerTest;
import common.commonbackend.user.SignUpController.UserAndJoinCode;
import common.commonbackend.user.SignUpController.UserWithoutHouse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static common.commonbackend.controllers.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
@AutoConfigureMockMvc(addFilters = false)
class SignUpControllerTest extends ControllerTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private static final String JOIN_CODE = "1234";

    @Test
    @SneakyThrows
    void shouldCreateUserAndHouse() {
        //given
        UserWithoutHouse userWithoutHouse = new UserWithoutHouse(USERNAME, PASSWORD);

        //when
        getMocMvc().perform(post("/api/createUserAndHouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userWithoutHouse)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(userService, times(1)).createUser(USERNAME, PASSWORD);
    }

    @Test
    @SneakyThrows
    void shouldCreateUserForExistingHouse() {
        //given
        UserAndJoinCode userAndJoinCode = new UserAndJoinCode(USERNAME, PASSWORD, JOIN_CODE);

        //when
        getMocMvc().perform(post("/api/createUserForExistingHouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userAndJoinCode)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(userService, times(1)).createUser(USERNAME, PASSWORD, JOIN_CODE);
    }

}