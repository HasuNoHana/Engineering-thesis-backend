package common.commonbackend.tasks;

import common.commonbackend.ControllerTest;
import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.Period;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest extends ControllerTest {

    private static final String ROOM_NAME = "Kuchnia";
    private static final String ROOM_IMAGE_URL = "url";
    private static final String TASK_NAME = "name";
    private static final int INITIAL_PRICE = 10;
    private static final boolean NOT_DONE = false;
    private static final boolean DONE = true;
    private static final long TASK_ID = 42L;
    private static final String TASK_NAME_2 = "task2";
    private static final int INITIAL_PRICE_2 = 20;
    private final HouseEntity house = new HouseEntity();
    private final Room room = new Room(1L, ROOM_NAME, ROOM_IMAGE_URL, house);
    private static final Period REPETITION_RATE = Period.ofDays(1);
    private static final LocalDate LAST_DONE_DATE = LocalDate.now();
    private final Task task = new Task(TASK_ID, TASK_NAME, INITIAL_PRICE, NOT_DONE, room, LAST_DONE_DATE, REPETITION_RATE);
    private final TaskDTO taskDTO = task.toDto();

    @Test
    void shouldDeleteTask() throws Exception {
        getMocMvc().perform(delete("/api/task?id=" + TASK_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(TASK_ID)))
                .andDo(print());
    }

    @Test
    void shouldPostTask() throws Exception {
        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(taskService.saveUpdatedTask(taskDTO, house)).thenReturn(task);

        //then
        getMocMvc().perform(post("/api/updateTask?id=" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task.toDto())));

        verify(taskService, org.mockito.Mockito.times(1)).saveUpdatedTask(taskDTO, house);
    }
}
