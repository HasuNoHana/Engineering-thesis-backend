package common.commonbackend.controllers;

import common.commonbackend.entities.Room;
import common.commonbackend.entities.Task;
import common.commonbackend.repositories.RoomRepository;
import common.commonbackend.repositories.TaskRepository;
import common.commonbackend.repositories.UserRepository;
import common.commonbackend.tasks.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static common.commonbackend.controllers.TestObjectMapperHelper.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private TaskService taskService;

    @Test
    public void shouldGetTaskById() throws Exception {
        //given
        Long taskId = 42L;
        Room room = new Room("Kuchnia", "url");
        Task task = new Task(taskId, "name", 10, false, room);

        //when
        when(taskService.getTask(taskId)).thenReturn(task);


        //then
        mockMvc.perform(get("/api/task?id=42"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task)))
                .andDo(print());
    }

    @Test
    public void shouldGetToDoTasks() throws Exception {
        //given
        Room room = new Room("Kuchnia", "url");
        List<Task> tasks = List.of(new Task("task1", 10, false, room),
                new Task("task2", 20, false, room));

        //when
        when(taskService.getToDoTasks()).thenReturn(tasks);

        //then
        mockMvc.perform(get("/api/todo_tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(tasks)))
                .andDo(print());
    }

    @Test
    public void shouldGetDoneTasks() throws Exception {
        //given
        Room room = new Room("Kuchnia", "url");
        List<Task> tasks = List.of(new Task("task3", 30, true, room));

        //when
        when(taskService.getDoneTasks()).thenReturn(tasks);

        //then
        mockMvc.perform(get("/api/done_tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(tasks)))
                .andDo(print());
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/task?id=42"))
                .andExpect(status().isOk())
                .andExpect(content().string("42"))
                .andDo(print());
    }

    @Test
    public void shouldPostTask() throws Exception {
        //given
        Room room = new Room("Kuchnia", "url");
        Task task = new Task("name", 10, false, room);

        //when
        when(taskService.saveTask(any())).thenReturn(task);

        //then
        mockMvc.perform(post("/api/task")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(task)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task)))
                .andDo(print());

        verify(taskService, org.mockito.Mockito.times(1)).saveTask(any()); // TODO could assert with exactly task from dto
    }
}
