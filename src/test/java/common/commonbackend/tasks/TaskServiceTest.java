package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.updatealgorithms.TaskPriceUpdaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@MockitoSettings
class TaskServiceTest {
    private static final String TASK_NAME = "TaskName";
    private static final long INITIAL_PRICE = 10;
    private static final boolean NOT_DONE = false;
    private static final boolean DONE = true;
    private static final long TASK_ID = 1L;
    private static final long ROOM_ID = 2L;
    private static final String TASK_NAME_2 = "task2";
    private static final int INITIAL_PRICE_2 = 20;
    private static final String ROOM_NAME = "Kuchnia";
    private static final String IMAGE_URL = "url";
    private static final long ROOM_ID_2 = 3L;
    private static final String ROOM_NAME_2 = "roomName2";
    private static final String IMAGE_URL_2 = "url2";
    private static final Period REPETITION_RATE_2 = Period.ofDays(6);
    private static final LocalDate LAST_DONE_DATE_2 = LocalDate.now().plusDays(3);
    private static final long USER_ID = 4L;
    @Mock
    TaskPriceUpdaterService taskPriceUpdaterService;
    @Mock
    TaskRepository taskRepository;
    @Mock
    RoomRepository roomRepository;
    @Mock
    HouseEntity house;
    private TaskService systemUnderTest;
    private static final Period REPETITION_RATE = Period.ofDays(1);
    private static final LocalDate PREVIOUS_LAST_DONE_DATE = LocalDate.now().minusDays(3);

    private static final LocalDate LAST_DONE_DATE = LocalDate.now();
    private static final long LAST_DONE_USER_ID = 1L;
    private static final long PREVIOUS_LAST_DONE_USER_ID = 2L;
    private final Room room = new Room(ROOM_ID, ROOM_NAME, IMAGE_URL, house);
    private final Task notDoneTask = new TaskBuilder()
            .setId(TASK_ID)
            .setName(TASK_NAME)
            .setInitialPrice(INITIAL_PRICE)
            .setDone(NOT_DONE).setRoom(room)
            .setLastDoneDate(LAST_DONE_DATE)
            .setPreviousLastDoneDate(PREVIOUS_LAST_DONE_DATE)
            .setLastDoneUserId(LAST_DONE_USER_ID)
            .setPreviousLastDoneUserId(PREVIOUS_LAST_DONE_USER_ID)
            .setRepetitionRate(REPETITION_RATE).createTask();

    private final TaskDTO notDoneTaskDTO = notDoneTask.toDto();
    private final TaskEntity notDoneTaskEntity = notDoneTask.toEntity();
    private final TaskEntity doneTaskEntity = new TaskEntity(TASK_ID, TASK_NAME, INITIAL_PRICE, DONE, room,
            LAST_DONE_DATE, PREVIOUS_LAST_DONE_DATE, LAST_DONE_USER_ID, PREVIOUS_LAST_DONE_USER_ID, REPETITION_RATE);
    private final Room room2 = new Room(ROOM_ID_2, ROOM_NAME_2, IMAGE_URL_2, house);

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
        TaskDTO taskDto = new TaskDTOBuilder().setId(TASK_ID).setName(TASK_NAME_2).setInitialPrice(INITIAL_PRICE_2).setCurrentPrice(null).setDone(NOT_DONE).setRoom(room2.toDto()).setLastDoneDate(LAST_DONE_DATE_2).setRepetitionRateInDays(REPETITION_RATE_2.getDays()).createTaskDTO();
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
        TaskEntity doneTaskEntity = new TaskEntity(TASK_ID, TASK_NAME, INITIAL_PRICE, DONE, room,
                LocalDate.now(), LAST_DONE_DATE, USER_ID, LAST_DONE_USER_ID, REPETITION_RATE);
        when(taskRepository.getTaskByIdAndRoom_House(TASK_ID, house)).thenReturn(notDoneTaskEntity);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(notDoneTask)).thenReturn(notDoneTask);
        when(taskRepository.save(any())).thenAnswer(returnsFirstArg());

        TaskService taskService = this.systemUnderTest;

        //when
        Task actual = taskService.setTaskDone(TASK_ID, house, DONE, USER_ID);

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