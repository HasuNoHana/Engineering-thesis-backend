package common.commonbackend.controllers;

import common.commonbackend.ControllerTest;
import common.commonbackend.dto.TaskDTO;
import common.commonbackend.entities.Room;
import common.commonbackend.entities.Task;
import common.commonbackend.house.HouseEntity;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.List;

import static common.commonbackend.controllers.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest extends ControllerTest {

    HouseEntity house = new HouseEntity();
    @Test
    @SneakyThrows
    public void shouldGetTaskById(){
        //given
        Long taskId = 42L;
        Room room = new Room("Kuchnia", "url",house);
        Task task = new Task(taskId, "name", 10, false, room);

        //when
        when(taskService.getTask(taskId, controllerHelper.getMyHouse())).thenReturn(task);


        //then
        getMocMvc().perform(get("/api/task?id=42"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task)))
                .andDo(print());
    }

    @Test
    public void shouldGetToDoTasks() throws Exception {
        //given
        Room room = new Room("Kuchnia", "url",house);
        List<Task> tasks = List.of(new Task("task1", 10, false, room),
                new Task("task2", 20, false, room));

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
        Room room = new Room("Kuchnia", "url",house);
        List<Task> tasks = List.of(new Task("task3", 30, true, room));

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
        getMocMvc().perform(delete("/api/task?id=42"))
                .andExpect(status().isOk())
                .andExpect(content().string("42"))
                .andDo(print());
    }

    @Test
    public void shouldPostTask() throws Exception {
        //given
        Room room = new Room(1L, "Kuchnia", "url", house);
        Task task = new Task("name", 10, false, room);
        TaskDTO taskDTO = new TaskDTO("name", 10, false, room.getId());

        //when
        when(taskService.saveUpdatedTask(1L, taskDTO)).thenReturn(task);

        //then
        getMocMvc().perform(post("/api/updateTask?id=1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task)))
                .andDo(print());

        verify(taskService, org.mockito.Mockito.times(1)).saveUpdatedTask(1L, taskDTO);
    }
}
