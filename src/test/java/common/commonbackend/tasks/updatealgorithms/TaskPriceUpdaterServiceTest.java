package common.commonbackend.tasks.updatealgorithms;

import common.commonbackend.rooms.Room;
import common.commonbackend.tasks.Task;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
class TaskPriceUpdaterServiceTest {

    @Mock
    Room room;

    @Test
    void shouldUpdateTask() {
        //given
        TaskPriceUpdateAlgorithm dumbAlgorithm = (price, lastDoneDate, period) -> 10;

        TaskPriceUpdaterService taskPriceUpdaterService = new TaskPriceUpdaterService(dumbAlgorithm);
        Task task = new Task("TaskName", 10, false, room);

        //when
        Task updatedTask = taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task);

        //then
        assertThat(task).isEqualTo(updatedTask);
    }

}