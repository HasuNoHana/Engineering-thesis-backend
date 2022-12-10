package common.commonbackend.controllers;

import common.commonbackend.entities.Room;
import common.commonbackend.repositories.RoomRepository;
import common.commonbackend.repositories.TaskRepository;
import common.commonbackend.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static common.commonbackend.controllers.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private TaskRepository taskRepository;


    @Test
    public void shouldGetRooms() throws Exception {
        //given
        List<Room> rooms = List.of(
                new Room(1L, "Kuchnia", "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg"),
                new Room(2L, "Łazienka", "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg"));

        //when
        when(roomRepository.findAll()).thenReturn(rooms);

        //then
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(rooms)))
                .andDo(print());
    }

}
