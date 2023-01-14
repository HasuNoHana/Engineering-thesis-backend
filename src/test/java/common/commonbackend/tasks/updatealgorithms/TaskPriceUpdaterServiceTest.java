package common.commonbackend.tasks.updatealgorithms;

import common.commonbackend.rooms.Room;
import common.commonbackend.tasks.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Period;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class TaskPriceUpdaterServiceTest {

    public static final String TASK_NAME = "TaskName";
    public static final long PRICE = 10L;
    public static final boolean NOT_DONE = false;
    public static final long USER_ID = 2L;
    public static final Period REPETITION_RATE = Period.ofDays(1);
    @Mock
    Room room;

    @Test
    void shouldUpdateTask() {
        //given
        TaskPriceUpdateAlgorithm dumbAlgorithm = (price, lastDoneDate, period) -> 10;

        TaskPriceUpdaterService taskPriceUpdaterService = new TaskPriceUpdaterService(dumbAlgorithm);
        Task task = new Task(TASK_NAME, PRICE, NOT_DONE, room, USER_ID, REPETITION_RATE);

        //when
        Task updatedTask = taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task);

        //then
        assertThat(task).isEqualTo(updatedTask);
    }

}