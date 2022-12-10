package common.commonbackend.house;

import common.commonbackend.entities.User;
import common.commonbackend.repositories.RoomRepository;
import common.commonbackend.repositories.TaskRepository;
import common.commonbackend.user.UserRepository;
import common.commonbackend.user.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static common.commonbackend.controllers.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HouseController.class)
@AutoConfigureMockMvc(addFilters = false)
class HouseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseService houseService;
    @MockBean
    private UserService userService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private TaskRepository taskRepository;
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
        mockMvc.perform(post("/api/house")
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