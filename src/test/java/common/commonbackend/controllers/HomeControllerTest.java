package common.commonbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.commonbackend.entities.Room;
import common.commonbackend.entities.Task;
import common.commonbackend.repositories.RoomRepository;
import common.commonbackend.repositories.TaskRepository;
import common.commonbackend.repositories.UserRepository;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HomeControllerTest {

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

    @Test
    public void home() throws Exception {
        mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(content().string("home"));
    }

    @Test
    public void shouldGetTaskById() throws Exception {
        //given
        Long taskId = 42L;
        Room room = new Room("Kuchnia", "url");
        Task task = new Task(taskId, "name", 10, false, room);

        //when
        when(taskRepository.getTaskById(taskId)).thenReturn(task);

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
        when(taskRepository.findTaskByDone(false)).thenReturn(tasks);

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
        when(taskRepository.findTaskByDone(true)).thenReturn(tasks);

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

        //then
        mockMvc.perform(post("/api/task")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(task)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task)))
                .andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
