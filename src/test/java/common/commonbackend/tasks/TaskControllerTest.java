package common.commonbackend.tasks;

import common.commonbackend.ControllerTest;
import common.commonbackend.house.HouseEntity;
import common.commonbackend.rooms.Room;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.List;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest extends ControllerTest {

    public static final String ROOM_NAME = "Kuchnia";
    public static final String ROOM_IMAGE_URL = "url";
    public static final String TASK_NAME = "name";
    public static final int INITIAL_PRICE = 10;
    public static final boolean NOT_DONE = false;
    public static final boolean DONE = true;
    public static final long TASK_ID = 42L;
    public static final String TASK_NAME_2 = "task2";
    public static final int INITIAL_PRICE_2 = 20;
    private final HouseEntity house = new HouseEntity();
    private final Room room = new Room(1L, ROOM_NAME, ROOM_IMAGE_URL, house);

    @Test
    @SneakyThrows
    public void shouldGetTaskById() {
        //given
        Task task = new Task(TASK_ID, TASK_NAME, INITIAL_PRICE, NOT_DONE, room);
        when(taskService.getTask(TASK_ID, controllerHelper.getMyHouse())).thenReturn(task);

        //then
        getMocMvc().perform(get("/api/task?id=" + TASK_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task)))
                .andDo(print());
    }

    @Test
    public void shouldGetToDoTasks() throws Exception {
        //given
        List<Task> tasks = List.of(
                new Task(TASK_NAME, INITIAL_PRICE, NOT_DONE, room),
                new Task(TASK_NAME_2, INITIAL_PRICE_2, NOT_DONE, room));

        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(taskService.getToDoTasks(house)).thenReturn(tasks);

        //then
        getMocMvc().perform(get("/api/todo_tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(tasks)))
                .andDo(print());
    }

    @Test
    public void shouldGetDoneTasks() throws Exception {
        //given
        List<Task> tasks = List.of(new Task(TASK_NAME, INITIAL_PRICE, DONE, room));

        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(taskService.getDoneTasks(house)).thenReturn(tasks);

        //then
        getMocMvc().perform(get("/api/done_tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(tasks)))
                .andDo(print());
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        getMocMvc().perform(delete("/api/task?id=" + TASK_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(TASK_ID)))
                .andDo(print());
    }

    @Test
    public void shouldPostTask() throws Exception {
        //given
        Task task = new Task(TASK_NAME, INITIAL_PRICE, NOT_DONE, room);
        TaskDTO taskDTO = new TaskDTO(TASK_NAME, INITIAL_PRICE, NOT_DONE, room.getId());

        //when
        when(taskService.saveUpdatedTask(TASK_ID, taskDTO)).thenReturn(task);

        //then
        getMocMvc().perform(post("/api/updateTask?id=" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task)))
                .andDo(print());

        verify(taskService, org.mockito.Mockito.times(1)).saveUpdatedTask(TASK_ID, taskDTO);
    }
}
