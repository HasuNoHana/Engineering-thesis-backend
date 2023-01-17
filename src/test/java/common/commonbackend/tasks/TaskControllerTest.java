package common.commonbackend.tasks;

import common.commonbackend.ControllerTest;
import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import common.commonbackend.users.User;
import common.commonbackend.users.house_buddy.HouseBuddy;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static common.commonbackend.tasks.TaskController.toDto;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockitoSettings
class TaskControllerTest extends ControllerTest {
    @Mock
    HouseBuddy houseBuddy;
    @Mock
    User user;

    private static final String ROOM_NAME = "Kuchnia";
    private static final String ROOM_IMAGE_URL = "url";
    private static final String TASK_NAME = "name";
    private static final int INITIAL_PRICE = 10;
    private static final boolean NOT_DONE = false;
    private static final boolean DONE = true;
    private static final long TASK_ID = 42L;
    private static final String TASK_NAME_2 = "task2";
    private static final int INITIAL_PRICE_2 = 20;
    public static final long USER_ID = 5L;
    private final HouseEntity house = new HouseEntity();
    private final Room room = new Room(1L, ROOM_NAME, ROOM_IMAGE_URL, house);
    private static final Period REPETITION_RATE = Period.ofDays(1);
    private static final LocalDate LAST_DONE_DATE = LocalDate.now();
    private final TaskBuilder taskBuilder = new TaskBuilder()
            .setId(TASK_ID)
            .setName(TASK_NAME)
            .setInitialPrice(INITIAL_PRICE)
            .setRoom(room)
            .setLastDoneDate(LAST_DONE_DATE)
            .setRepetitionRate(REPETITION_RATE);
    private final Task task = taskBuilder
            .setDone(NOT_DONE).createTask();
    private final TaskDTO taskDTO = task.toDto();

    @Test
    @SneakyThrows
    void shouldGetTasks() {
        //when
        List<Task> tasks = List.of(task);
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(taskService.getTasks(house)).thenReturn(tasks);

        //then
        getMocMvc().perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(toDto(tasks))));

        verify(taskService, times(1)).getTasks(house);
    }

    @Test
    @SneakyThrows
    void shouldDeleteTask() {
        getMocMvc().perform(delete("/api/task?id=" + TASK_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(TASK_ID)))
                .andDo(print());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(taskService.saveUpdatedTask(taskDTO, house)).thenReturn(task);

        //then
        getMocMvc().perform(post("/api/updateTask?id=" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task.toDto())));

        verify(taskService, times(1)).saveUpdatedTask(taskDTO, house);
    }

    @Test
    void shouldAddTask() throws Exception {
        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(taskService.saveNewTask(taskDTO, house)).thenReturn(task);

        //then
        getMocMvc().perform(post("/api/addTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task.toDto())));

        verify(taskService, times(1)).saveNewTask(taskDTO, house);
    }

    @Test
    @SneakyThrows
    void shouldMakeTaskDone() {
        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(controllerHelper.getMyUser()).thenReturn(user);
        Task taskDone = taskBuilder.setDone(DONE).createTask();
        when(taskService.setTaskDone(TASK_ID, house, DONE, user)).thenReturn(taskDone);


        //then
        getMocMvc().perform(post("/api/makeTaskDone?id=" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(taskDone.toDto())));

        verify(taskService, times(1))
                .setTaskDone(TASK_ID, house, DONE, user);
    }

    @Test
    @SneakyThrows
    void shouldMakeTaskNotDone() {
        //when
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(controllerHelper.getMyUser()).thenReturn(user);
        Task taskDone = taskBuilder.setDone(NOT_DONE).createTask();
        TaskDTO taskDTO = taskDone.toDto();
        when(taskService.setTaskDone(TASK_ID, house, NOT_DONE, user)).thenReturn(taskDone);


        //then
        getMocMvc().perform(post("/api/makeTaskToDo?id=" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(taskDone.toDto())));

        verify(taskService, times(1))
                .setTaskDone(TASK_ID, house, NOT_DONE, user);
    }
}
