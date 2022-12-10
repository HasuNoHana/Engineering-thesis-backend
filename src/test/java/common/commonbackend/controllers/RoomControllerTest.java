package common.commonbackend.controllers;

import common.commonbackend.ControllerTest;
import common.commonbackend.entities.Room;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static common.commonbackend.controllers.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RoomControllerTest extends ControllerTest {

    @Test
    public void shouldGetRooms() throws Exception {
        //given
        List<Room> rooms = List.of(
                new Room(1L, "Kuchnia", "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg"),
                new Room(2L, "Łazienka", "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg"));

        //when
        when(roomRepository.findAll()).thenReturn(rooms);

        //then
        getMocMvc().perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(rooms)))
                .andDo(print());
    }

}
