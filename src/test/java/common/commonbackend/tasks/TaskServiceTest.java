package common.commonbackend.tasks;

import common.commonbackend.dto.RoomDTO;
import common.commonbackend.entities.Room;
import common.commonbackend.entities.Task;
import common.commonbackend.house.HouseEntity;
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

    @Mock
    HouseEntity house;

    @Test
    void shouldGetTask() {
        //given
        Task task = new Task(1L,"TaskName", 10, false, room);

        when(taskRepository.getTaskByIdAndRoom_House(1L,house)).thenReturn(task);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task)).thenReturn(task);

        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);

        //when
        Task recivedTask = taskService.getTask(1L, house);

        //then
        assertThat(task).isEqualTo(recivedTask);
    }

    @Test
    void shouldGetToDoTasks() {
        //given
        List<Task> tasks = List.of(new Task("task1", 10, false, room),
                new Task("task2", 20, false, room));

        when(taskRepository.findTaskByDoneAndRoom_House(false, house)).thenReturn(tasks);
        when(taskPriceUpdaterService.getTasksWithUpdatedPrice(tasks)).thenReturn(tasks);

        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);

        //when
        List<Task> recivedTasks = taskService.getToDoTasks(house);

        //then
        assertThat(tasks).isEqualTo(recivedTasks);
    }

    @Test
    void shouldGetDoneTasks() {
        //given
        List<Task> tasks = List.of(new Task("task1", 10, false, room),
                new Task("task2", 20, false, room));

        when(taskRepository.findTaskByDoneAndRoom_House(true, house)).thenReturn(tasks);
        when(taskPriceUpdaterService.getTasksWithUpdatedPrice(tasks)).thenReturn(tasks);

        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);

        //when
        List<Task> recivedTasks = taskService.getDoneTasks(house);

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

//    @Test
//    void shouldSaveTask() { // TODO task should not create new room when fromDto is called. It should use existing room
//        //given
//        TaskDTO taskDTO = new TaskDTO();
//        taskDTO.setName("TaskName");
//        taskDTO.setPrice(10);
//        taskDTO.setDone(false);
//        taskDTO.setRoom(roomDTO);
//        Task task = Task.fromDto(taskDTO);
//
//        when(taskRepository.save(task)).thenReturn(task);
//        TaskService taskService = new TaskService(taskRepository,taskPriceUpdaterService);
//
//        //when
//        taskService.saveTask(taskDTO);
//
//        //then
//        verify(taskRepository, times(1)).save(any());
//    }

}