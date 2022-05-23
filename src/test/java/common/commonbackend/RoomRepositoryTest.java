package common.commonbackend;

import common.commonbackend.entities.Room;
import common.commonbackend.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
