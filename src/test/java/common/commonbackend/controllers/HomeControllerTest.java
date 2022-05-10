package common.commonbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.commonbackend.controllers.HomeController;
import common.commonbackend.entities.Task;
import common.commonbackend.repositories.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

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
        Task task = new Task(taskId, "name");

        //when
        when(taskRepository.getTaskById(taskId)).thenReturn(task);

        //then
        mockMvc.perform(get("/api/task?id=42"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(task)))
                .andDo(print());
    }

    @Test
    public void shouldGetTasks() throws Exception {
        //given
        List<Task> tasks = List.of(new Task("task1"),
                new Task("task2"),
                new Task("task3"));

        //when
        when(taskRepository.findAll()).thenReturn(tasks);

        //then
        mockMvc.perform(get("/api/tasks"))
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
        Task task = new Task(42L, "name");

        //when
        when(taskRepository.getTaskById(42L)).thenReturn(task);

        //then
        mockMvc.perform(post("/api/task?id=42&name=name"))
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
