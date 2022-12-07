package common.commonbackend.tasks;

import common.commonbackend.dto.RoomDTO;
import common.commonbackend.dto.TaskDTO;
import common.commonbackend.entities.Room;
import common.commonbackend.entities.Task;
import common.commonbackend.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@MockitoSettings
class TaskServiceTest {

    @Mock
    TaskPriceUpdaterService taskPriceUpdaterService;

    @Mock
    Room room;

    @Mock
    RoomDTO roomDTO;

    @Mock
    TaskRepository taskRepository;

    @Test
    void shouldGetTask() {
        //given
        Task task = new Task(1L,"TaskName", 10, false, room);

        when(taskRepository.getTaskById(1L)).thenReturn(task);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task)).thenReturn(task);

        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);

        //when
        Task recivedTask = taskService.getTask(1L);

        //then
        assertThat(task).isEqualTo(recivedTask);
    }

    @Test
    void shouldGetToDoTasks() {
        //given
        List<Task> tasks = List.of(new Task("task1", 10, false, room),
                new Task("task2", 20, false, room));

        when(taskRepository.findTaskByDone(false)).thenReturn(tasks);
        when(taskPriceUpdaterService.getTasksWithUpdatedPrice(tasks)).thenReturn(tasks);

        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);

        //when
        List<Task> recivedTasks = taskService.getToDoTasks();

        //then
        assertThat(tasks).isEqualTo(recivedTasks);
    }

    @Test
    void shouldGetDoneTasks() {
        //given
        List<Task> tasks = List.of(new Task("task1", 10, false, room),
                new Task("task2", 20, false, room));

        when(taskRepository.findTaskByDone(true)).thenReturn(tasks);
        when(taskPriceUpdaterService.getTasksWithUpdatedPrice(tasks)).thenReturn(tasks);

        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);

        //when
        List<Task> recivedTasks = taskService.getDoneTasks();

        //then
        assertThat(tasks).isEqualTo(recivedTasks);
    }

    @Test
    void shouldDeleteTask() {
        //given
        Task task = new Task(1L,"TaskName", 10, false, room);
        when(taskRepository.getTaskById(1L)).thenReturn(task);
        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);

        //when
        taskService.deleteTask(1L);

        //then
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void shouldSaveTask() {
        //given
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("TaskName");
        taskDTO.setPrice(10);
        taskDTO.setDone(false);
        taskDTO.setRoom(roomDTO);
        Task task = Task.fromDto(taskDTO);

        when(taskRepository.save(any())).thenReturn(task);
        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);

        //when
        taskService.saveTask(taskDTO);

        //then
        verify(taskRepository, times(1)).save(any());
    }

}