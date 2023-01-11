package common.commonbackend.houses;

import common.commonbackend.ControllerTest;
import common.commonbackend.users.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseController.class)
@AutoConfigureMockMvc(addFilters = false)
class HouseControllerTest extends ControllerTest {

    private final String JOIN_CODE = "1234";

    private final long USER_ID = 1L;
    @Mock
    private User user;

    @Mock
    private HouseEntity houseEntity;

    @SneakyThrows
    @Test
    void shouldCreateHouse() {
        //given
        when(houseEntity.getJoinCode()).thenReturn(JOIN_CODE);
        when(userService.getUserById(USER_ID)).thenReturn(user);
        when(houseService.createHouseForUser(user)).thenReturn(houseEntity);

        //when
        getMocMvc().perform(post("/api/createHouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(USER_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(JOIN_CODE));

        //then
        verify(houseService, times(1)).createHouseForUser(user);
        verify(userService, times(1)).getUserById(USER_ID);
    }

}