package common.commonbackend.house;

import common.commonbackend.ControllerTest;
import common.commonbackend.entities.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static common.commonbackend.controllers.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseController.class)
@AutoConfigureMockMvc(addFilters = false)
class HouseControllerTest extends ControllerTest {
    @Mock
    private User user;

    @SneakyThrows
    @Test
    void shouldPostTask() {
        //given
        long userId = 1;
        String joinCode = "{\"joinCode\":\"dupa\"}"; // TODO fix me when join code is implemented

        when(userService.getUserById(userId)).thenReturn(user);
        when(houseService.createHouseForUser(user)).thenReturn(joinCode);

        //when
        getMocMvc().perform(post("/api/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userId)))
                .andExpect(status().isOk())
                .andExpect(content().json(joinCode))
                .andDo(print());

        //then
        verify(houseService, times(1)).createHouseForUser(user);
        verify(userService, times(1)).getUserById(userId);
    }
}