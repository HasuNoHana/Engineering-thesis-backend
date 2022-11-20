package common.commonbackend.tasks;

import common.commonbackend.entities.Room;
import common.commonbackend.entities.Task;
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
        TaskPriceUpdateAlgorithm taskPriceUpdateAlgorithm = new TaskPriceUpdateAlgorithm() {
            @Override
            public int getNewPrice(int price, java.time.LocalDate lastDoneDate, java.time.Period period) {
                return 10;
            }
        };

        TaskPriceUpdaterService taskPriceUpdaterService = new TaskPriceUpdaterService(taskPriceUpdateAlgorithm);
        Task task = new Task("TaskName", 10, false, room);

        //when
        Task updatedTask = taskPriceUpdaterService.updateTask(task);

        //then
        assertThat(task)
                .isEqualTo(updatedTask);
    }

}