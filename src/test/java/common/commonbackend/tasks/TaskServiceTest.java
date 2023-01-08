package common.commonbackend.tasks;

import common.commonbackend.house.HouseEntity;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.updatealgorithms.TaskPriceUpdaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@MockitoSettings
class TaskServiceTest {
    public static final String TASK_NAME = "TaskName";
    public static final long INITIAL_PRICE = 10;
    public static final boolean NOT_DONE = false;
    public static final boolean DONE = true;
    public static final long TASK_ID = 1L;
    public static final long ROOM_ID = 1L;
    public static final String TASK_NAME_2 = "task2";
    public static final int INITIAL_PRICE_2 = 20;
    @Mock
    TaskPriceUpdaterService taskPriceUpdaterService;
    @Mock
    Room room;
    @Mock
    TaskRepository taskRepository;
    @Mock
    RoomRepository roomRepository;
    @Mock
    HouseEntity house;
    private TaskService systemUnderTest;

    @BeforeEach
    void setUp() {
        systemUnderTest = new TaskService(taskRepository, roomRepository, taskPriceUpdaterService);
    }

    @Test
    void shouldGetTask() {
        //given
        Task task = new Task(TASK_ID, TASK_NAME, INITIAL_PRICE, NOT_DONE, room);

        when(taskRepository.getTaskByIdAndRoom_House(TASK_ID, house)).thenReturn(task);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task)).thenReturn(task);

        //when
        Task receivedTask = systemUnderTest.getTask(TASK_ID, house);

        //then
        assertThat(task).isEqualTo(receivedTask);
    }

    @Test
    void shouldGetToDoTasks() {
        //given
        List<Task> tasks = List.of(
                new Task(TASK_NAME, INITIAL_PRICE, NOT_DONE, room),
                new Task(TASK_NAME_2, INITIAL_PRICE_2, NOT_DONE, room));

        when(taskRepository.findTaskByDoneAndRoom_House(NOT_DONE, house)).thenReturn(tasks);
        when(taskPriceUpdaterService.getTasksWithUpdatedPrice(tasks)).thenReturn(tasks);

        //when
        List<Task> receivedTasks = systemUnderTest.getToDoTasks(house);

        //then
        assertThat(tasks).isEqualTo(receivedTasks);
    }

    @Test
    void shouldGetDoneTasks() {
        //given
        List<Task> tasks = List.of(
                new Task(TASK_NAME, INITIAL_PRICE, DONE, room),
                new Task(TASK_NAME_2, INITIAL_PRICE_2, DONE, room));

        when(taskRepository.findTaskByDoneAndRoom_House(DONE, house)).thenReturn(tasks);
        when(taskPriceUpdaterService.getTasksWithUpdatedPrice(tasks)).thenReturn(tasks);

        //when
        List<Task> receivedTasks = systemUnderTest.getDoneTasks(house);

        //then
        assertThat(tasks).isEqualTo(receivedTasks);
    }

    @Test
    void shouldDeleteTask() {
        //given
        Task task = new Task(TASK_ID, TASK_NAME, INITIAL_PRICE, NOT_DONE, room);
        when(taskRepository.getTaskById(TASK_ID)).thenReturn(task);

        //when
        systemUnderTest.deleteTask(TASK_ID);

        //then
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void shouldSaveUpdatedTask() {
        //given
        Task task = new Task(TASK_ID, TASK_NAME, INITIAL_PRICE, NOT_DONE, room);
        TaskDTO taskDTO = new TaskDTO(TASK_NAME, INITIAL_PRICE, NOT_DONE, ROOM_ID);
        when(taskRepository.getTaskById(TASK_ID)).thenReturn(task);

        //when
        systemUnderTest.saveUpdatedTask(TASK_ID, taskDTO, house);

        //then
        verify(taskRepository, times(1)).getTaskById(TASK_ID);
        verify(roomRepository, times(1)).getRoomByIdAndHouse(ROOM_ID, house);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void shouldSaveNewTask() {
        //given
        TaskDTO taskDTO = new TaskDTO(TASK_NAME, INITIAL_PRICE, NOT_DONE, ROOM_ID);
        when(taskRepository.save(any())).thenAnswer(returnsFirstArg());
        when(roomRepository.getRoomByIdAndHouse(ROOM_ID, house)).thenReturn(room);

        //when
        Task actual = systemUnderTest.saveNewTask(taskDTO, house);

        //then
        assertThat(actual)
                .extracting(
                        Task::isDone,
                        Task::getInitialPrice,
                        Task::getName,
                        Task::getRoom)
                .contains(
                        taskDTO.isDone(),
                        taskDTO.getPrice(),
                        taskDTO.getName(),
                        room);

        verify(roomRepository, times(1)).getRoomByIdAndHouse(ROOM_ID, house);
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void shouldSetTaskDone() {
        //given
        Task task = new Task(TASK_ID, TASK_NAME, INITIAL_PRICE, NOT_DONE, room);
        when(taskRepository.getTaskByIdAndRoom_House(TASK_ID, house)).thenReturn(task);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task)).thenReturn(task);
        when(taskRepository.save(any())).thenAnswer(returnsFirstArg());

        TaskService taskService = this.systemUnderTest;

        //when
        Task actual = taskService.setTaskDone(TASK_ID, house, DONE);

        //then
        assertThat(actual)
                .extracting(
                        Task::isDone,
                        Task::getInitialPrice,
                        Task::getName,
                        Task::getRoom,
                        Task::getId)
                .contains(
                        DONE,
                        INITIAL_PRICE,
                        TASK_NAME,
                        room,
                        TASK_ID);

        verify(taskRepository, times(1)).save(task);
    }
}