package common.commonbackend.users;

import common.commonbackend.ControllerTest;
import common.commonbackend.houses.HouseEntity;
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
    public static final String USERNAME = "zuza";
    public static final String PASSWORD = "haslo";
    public static final long FIREWOOD_STACK_SIZE = 0L;
    public static final long WEEKLY_POINTS_CONTRIBUTION = 100L;
    public static final String IMAGE = "image";

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