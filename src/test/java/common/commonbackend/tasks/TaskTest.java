package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    private static final HouseEntity house = new HouseEntity(); //TODO room and house also should be moved to Entity and domain object. We should test here whole mapping to check if not detalis are misisng
    private static final Room room = new Room(2L, "RoomName", "imageUrl", house);
    private static final Task task = new Task(1L, "TaskName", 10, false, room);

    @Test
    void shouldMapToAndFromEntity() {
        //when
        TaskEntity taskEntity = task.toEntity();
        Task actualTask = Task.fromEntity(taskEntity);

        //then
        assertEquals(task, actualTask);
    }

    @Test
    @Disabled("Read how this should be handle and then fix this test")
    void shouldMapToAndFromDto() {
        //when
        TaskDTO taskDTO = task.toDto();
        Task actualTask = Task.fromDTOAndRoom(taskDTO);

        //then
        assertEquals(task, actualTask);
    }

}