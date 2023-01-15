package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    private static final Period REPETITION_RATE = Period.ofDays(1);
    private static final LocalDate LAST_DONE_DATE = LocalDate.now();
    private static final HouseEntity house = new HouseEntity(); //TODO room and house also should be moved to Entity and domain object. We should test here whole mapping to check if not detalis are misisng
    private static final Room room = new Room(2L, "RoomName", "imageUrl", house);
    private static final Task task = new Task(1L, "TaskName", 10, Optional.of(10L), false, room, LAST_DONE_DATE, REPETITION_RATE);

    @Test
    void shouldMapToAndFromEntity() {
        //when
        TaskEntity taskEntity = task.toEntity();
        Task actualTask = Task.fromEntity(taskEntity);

        //then
        assertThat(actualTask)
                .extracting(
                        Task::isDone,
                        Task::getInitialPrice,
                        Task::getName,
                        Task::getRoom,
                        Task::getLastDoneDate,
                        Task::getRepetitionRate)
                .contains(
                        task.isDone(),
                        task.getInitialPrice(),
                        task.getName(),
                        room,
                        task.getLastDoneDate(),
                        task.getRepetitionRate());
    }

    @Test
    void shouldMapToAndFromDto() {
        //when
        TaskDTO taskDTO = task.toDto();
        Task actualTask = Task.fromDto(taskDTO);

        //then
        assertEquals(task, actualTask);
    }

}