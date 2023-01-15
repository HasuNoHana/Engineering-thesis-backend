package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.updatealgorithms.TaskPriceUpdaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@MockitoSettings
class TaskServiceTest {
    private static final String TASK_NAME = "TaskName";
    public static final long INITIAL_PRICE = 10;
    public static final boolean NOT_DONE = false;
    public static final boolean DONE = true;
    public static final long TASK_ID = 1L;
    public static final long ROOM_ID = 1L;
    public static final String TASK_NAME_2 = "task2";
    public static final int INITIAL_PRICE_2 = 20;
    public static final String ROOM_NAME = "Kuchnia";
    public static final String IMAGE_URL = "url";
    private static final long ROOM_ID_2 = 3L;
    private static final String ROOM_NAME_2 = "roomName2";
    private static final String IMAGE_URL_2 = "url2";
    @Mock
    TaskPriceUpdaterService taskPriceUpdaterService;
    @Mock
    TaskRepository taskRepository;
    @Mock
    RoomRepository roomRepository;
    @Mock
    HouseEntity house;
    private TaskService systemUnderTest;

    private final Room room = new Room(ROOM_ID, ROOM_NAME, IMAGE_URL, house);
    private final Task notDoneTask = new Task(TASK_ID, TASK_NAME, INITIAL_PRICE, NOT_DONE, room);
    ;
    private final TaskDTO notDoneTaskDTO = notDoneTask.toDto();
    private final TaskEntity notDoneTaskEntity = notDoneTask.toEntity();
    private final TaskEntity doneTaskEntity = new TaskEntity(TASK_ID, TASK_NAME, INITIAL_PRICE, DONE, room);
    private Room room2 = new Room(ROOM_ID_2, ROOM_NAME_2, IMAGE_URL_2, house);

    @BeforeEach
    void setUp() {
        systemUnderTest = new TaskService(taskRepository, roomRepository, taskPriceUpdaterService);
    }

    @Test
    void shouldGetTask() {
        //given
        when(taskRepository.getTaskByIdAndRoom_House(TASK_ID, house)).thenReturn(notDoneTaskEntity);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(any())).thenAnswer(returnsFirstArg());

        //when
        Task receivedTask = systemUnderTest.getTask(TASK_ID, house);

        //then
        assertThat(receivedTask).isEqualTo(notDoneTask);
    }
    @Test
    void shouldDeleteTask() {
        //given
        when(taskRepository.getTaskById(TASK_ID)).thenReturn(notDoneTaskEntity);

        //when
        systemUnderTest.deleteTask(TASK_ID);

        //then
        verify(taskRepository, times(1)).delete(notDoneTaskEntity);
    }

    @Test
    void shouldSaveUpdatedTask() {
        //given
        TaskDTO taskDto = new TaskDTO(TASK_ID, TASK_NAME_2, INITIAL_PRICE_2, NOT_DONE, ROOM_ID_2);
        when(taskRepository.getTaskById(TASK_ID)).thenReturn(notDoneTaskEntity);
        when(roomRepository.getRoomByIdAndHouse(ROOM_ID_2, house)).thenReturn(room2);
        when(taskRepository.save(any())).thenAnswer(returnsFirstArg());

        //when
        Task actual = systemUnderTest.saveUpdatedTask(taskDto, house);

        //then
        assertThat(actual)
                .extracting(
                        Task::isDone,
                        Task::getInitialPrice,
                        Task::getName,
                        Task::getRoom)
                .contains(
                        notDoneTaskDTO.isDone(),
                        taskDto.getInitialPrice(),
                        taskDto.getName(),
                        room2);


        verify(taskRepository, times(1)).getTaskById(TASK_ID);
        verify(roomRepository, times(1)).getRoomByIdAndHouse(ROOM_ID_2, house);
    }

    @Test
    void shouldSaveNewTask() {
        //given
        when(taskRepository.save(any())).thenAnswer(returnsFirstArg());
        when(roomRepository.getRoomByIdAndHouse(ROOM_ID, house)).thenReturn(room);

        //when
        Task actual = systemUnderTest.saveNewTask(notDoneTaskDTO, house);

        //then
        assertThat(actual)
                .extracting(
                        Task::isDone,
                        Task::getInitialPrice,
                        Task::getName,
                        Task::getRoom)
                .contains(
                        notDoneTaskDTO.isDone(),
                        notDoneTaskDTO.getInitialPrice(),
                        notDoneTaskDTO.getName(),
                        room);

        verify(roomRepository, times(1)).getRoomByIdAndHouse(ROOM_ID, house);
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void shouldSetTaskDone() {
        //given
        when(taskRepository.getTaskByIdAndRoom_House(TASK_ID, house)).thenReturn(notDoneTaskEntity);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(notDoneTask)).thenReturn(notDoneTask);
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

        verify(taskRepository, times(1)).save(doneTaskEntity);
    }
}