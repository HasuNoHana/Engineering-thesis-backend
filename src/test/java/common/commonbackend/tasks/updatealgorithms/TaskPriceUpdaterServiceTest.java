package common.commonbackend.tasks.updatealgorithms;

import common.commonbackend.rooms.Room;
import common.commonbackend.tasks.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class TaskPriceUpdaterServiceTest {

    @Mock
    Room room;

    @Test
    void shouldUpdateTaskPrice() {
        //given
        TaskPriceUpdateAlgorithm dumbAlgorithm = (price, lastDoneDate, period) -> 10;

        TaskPriceUpdaterService taskPriceUpdaterService = new TaskPriceUpdaterService(dumbAlgorithm);
        Task task = new Task(1L, "TaskName", 10, false, room, LocalDate.now(), Period.ofDays(1));

        //when
        Task updatedTask = taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task);

        //then
        assertThat(task).isEqualTo(updatedTask);
    }

}