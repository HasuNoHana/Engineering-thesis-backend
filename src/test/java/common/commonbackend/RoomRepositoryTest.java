package common.commonbackend;

import common.commonbackend.entities.Room;
import common.commonbackend.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void shouldAddTaskToDatabase() {
        //given
        roomRepository.deleteAll(); //remove tasks from different tests
        Room room = new Room("name", "url");

        //when
        roomRepository.save(room);

        //then
        assertThat(roomRepository.count()).isEqualTo(1);
        assertThat(roomRepository.getRoomById(room.getId())).isEqualTo(room);
    }

    @Test
    void shouldGetRoomsFromDatabase() {
        //given
        roomRepository.deleteAll(); //remove tasks from different tests
        List<Room> rooms = List.of(
                new Room("Sypialnia", "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg"),
                new Room("Kuchnia", "https://upload.wikimedia.org/wikipedia/commons/b/b8/L_K%C3%BCche_2015.jpg"),
                new Room("Łazienka", "https://upload.wikimedia.org/wikipedia/commons/8/8a/Bathroom_with_tub_and_fireplace_%28Pleasure_Point_Roadhouse%2C_Monterey_Bay%2C_California_-_30_September%2C_2002%29.jpg")
        );

        //then
        assertThat(roomRepository.findAll()).isEqualTo(rooms);
    }
}
