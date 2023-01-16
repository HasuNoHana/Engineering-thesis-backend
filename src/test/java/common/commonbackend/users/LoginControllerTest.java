package common.commonbackend.users;

import common.commonbackend.ControllerTest;
import common.commonbackend.houses.HouseEntity;
import common.commonbackend.users.house_buddy.HouseBuddy;
import common.commonbackend.users.login.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest extends ControllerTest {
    private static final String USERNAME = "zuza";
    private static final String PASSWORD = "haslo";
    private static final long FIREWOOD_STACK_SIZE = 0L;
    private static final long WEEKLY_POINTS_CONTRIBUTION = 100L;
    private static final String IMAGE = "image";

    @Test
    void shouldGetUser() throws Exception {
        //given
        HouseEntity house = new HouseEntity();
        HouseBuddy houseBuddy = new HouseBuddy(FIREWOOD_STACK_SIZE, WEEKLY_POINTS_CONTRIBUTION, IMAGE, house);
        User user = new User(USERNAME, PASSWORD, houseBuddy);

        //then
        getMocMvc().perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

}