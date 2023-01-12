package common.commonbackend.users;

import common.commonbackend.ControllerTest;
import common.commonbackend.houses.HouseEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockitoSettings
class UserControllerTest extends ControllerTest {

    public static final long USER_ID = 1L;
    public static final String USERNAME = "zuza";
    public static final Long FIREWOOD_STACK_SIZE = 40L;

    public static final long DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION = 100L;
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg";

    @Mock
    User user;

    @Mock
    HouseEntity house;

    @Mock
    HouseBuddy houseBuddy;

    @Test
    @SneakyThrows
    void shouldGetUser() {
        //given
        UserDTO userDTO = new UserDTO(USER_ID, USERNAME, FIREWOOD_STACK_SIZE, DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION,
                DEFAULT_IMAGE);
        when(controllerHelper.getMyUser()).thenReturn(user);
        when(user.getHouseBuddy()).thenReturn(houseBuddy);
        when(houseBuddyService.createUserDTOFromHouseBuddy(houseBuddy)).thenReturn(userDTO);

        //when
        getMocMvc().perform(get("/api/currentUserData"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userDTO)))
                .andDo(print());

        //then
    }

}