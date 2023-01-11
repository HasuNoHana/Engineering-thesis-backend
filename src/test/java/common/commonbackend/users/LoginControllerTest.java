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

    @Test
    void shouldGetUser() throws Exception {
        //given
        HouseEntity house = new HouseEntity();
        User user = new User(USERNAME, PASSWORD, house);

        //then
        getMocMvc().perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

}