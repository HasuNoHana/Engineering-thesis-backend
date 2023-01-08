package common.commonbackend.rooms;

import common.commonbackend.ControllerTest;
import common.commonbackend.house.HouseEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.List;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoomControllerTest extends ControllerTest {

    private static final String IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg";
    public static final String ROOM_NAME = "Kitchen";
    public static final String ROOM_NAME_2 = "Bathroom";

    @Test
    @SneakyThrows
    void shouldGetRooms() {
        //given
        HouseEntity house = new HouseEntity();

        List<Room> rooms = List.of(
                new Room(ROOM_NAME, IMAGE_URL, house),
                new Room(ROOM_NAME_2, IMAGE_URL, house));

        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(roomService.getRoomsForHouse(house)).thenReturn(rooms);

        //then
        getMocMvc().perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(rooms)))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void shouldCreateRoom() {
        //given
        HouseEntity house = new HouseEntity();
        RoomDTO roomDTO = new RoomDTO(ROOM_NAME, IMAGE_URL);
        Room room = new Room(ROOM_NAME, IMAGE_URL, house);

        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(roomService.createRoom(roomDTO, house)).thenReturn(room);

        //then
        getMocMvc().perform(post("/api/addRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(roomDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(room)))
                .andDo(print());
    }

}
