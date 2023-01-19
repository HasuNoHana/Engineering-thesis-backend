package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.updatealgorithms.TaskPriceUpdaterService;
import common.commonbackend.users.User;
import common.commonbackend.users.house_buddy.HouseBuddyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@MockitoSettings
class TaskServiceTest {
    private static final long CURRENT_PRICE = 15L;
    @Mock
    TaskPriceUpdaterService taskPriceUpdaterService;
    @Mock
    TaskRepository taskRepository;
    @Mock
    RoomRepository roomRepository;
    @Mock
    HouseBuddyService houseBuddyService;
    @Mock
    HouseEntity house;
    @Mock
    User user;
    private static final String TASK_NAME = "TaskName";
    private static final long INITIAL_PRICE = 10;
    private static final long LAST_DONE_PRICE = CURRENT_PRICE;
    private static final boolean NOT_DONE = false;
    private static final boolean DONE = true;
    private static final long TASK_ID = 1L;
    private static final long TASK_ID_2 = 2L;
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
    private TaskService systemUnderTest;
    private static final Period REPETITION_RATE = Period.ofDays(1);
    private static final LocalDate PREVIOUS_LAST_DONE_DATE = LocalDate.now().minusDays(3);

    private static final LocalDate LAST_DONE_DATE = LocalDate.now();
    private static final LocalDate BEGIN_PERIOD_DATE = LocalDate.now();
    private static final long LAST_DONE_USER_ID = 1L;
    private static final long PREVIOUS_LAST_DONE_USER_ID = 2L;
    private final Room room = new Room(ROOM_ID, ROOM_NAME, IMAGE_URL, house);
    private final TaskBuilder taskBuilder = new TaskBuilder()
            .setName(TASK_NAME)
            .setInitialPrice(INITIAL_PRICE)
            .setDone(NOT_DONE).setRoom(room)
            .setLastDoneDate(LAST_DONE_DATE)
            .setPreviousLastDoneDate(PREVIOUS_LAST_DONE_DATE)
            .setLastDoneUserId(LAST_DONE_USER_ID)
            .setPreviousLastDoneUserId(PREVIOUS_LAST_DONE_USER_ID)
            .setRepetitionRate(REPETITION_RATE)
            .setBeginPeriodDate(BEGIN_PERIOD_DATE)
            .setLastDonePrice(LAST_DONE_PRICE);
    private final Task notDoneTask = taskBuilder.setId(TASK_ID).createTask();
    private final Task secondTask = taskBuilder.setId(TASK_ID_2).createTask();

    private final TaskDTO notDoneTaskDTO = notDoneTask.toDto();
    private final TaskEntity notDoneTaskEntity = notDoneTask.toEntity();
    private final TaskEntity doneTaskEntity = new TaskEntityBuilder().setId(TASK_ID).setName(TASK_NAME).setInitialPrice(INITIAL_PRICE).setDone(DONE).setRoom(room).setLastDoneDate(LAST_DONE_DATE).setPreviousLastDoneDate(PREVIOUS_LAST_DONE_DATE).setLastDoneUserId(LAST_DONE_USER_ID).setPreviousLastDoneUserId(PREVIOUS_LAST_DONE_USER_ID).setRepetitionRate(REPETITION_RATE).setBeginPeriodDate(BEGIN_PERIOD_DATE).setLastDonePrice(LAST_DONE_PRICE).createTaskEntity();
    private final Room room2 = new Room(ROOM_ID_2, ROOM_NAME_2, IMAGE_URL_2, house);

    @BeforeEach
    void setUp() {
        systemUnderTest = new TaskService(taskRepository, roomRepository, taskPriceUpdaterService, houseBuddyService);
    }

    @Test
    void shouldGetTasks() {
        //given
        List<Task> expectedTasks = List.of(notDoneTask, secondTask);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(any())).thenAnswer(returnsFirstArg());
        when(taskRepository.findTasksByRoom_House(house))
                .thenReturn(expectedTasks.stream().map(Task::toEntity).collect(Collectors.toList()));
        when(taskPriceUpdaterService.getTasksWithUpdatedPrice(any())).thenAnswer(returnsFirstArg());

        //when
        List<Task> receivedTasks = systemUnderTest.getTasks(house);

        //then
        assertThat(receivedTasks).isEqualTo(expectedTasks);
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
        Task notDoneTask = new TaskBuilder()
                .setName(TASK_NAME)
                .setInitialPrice(INITIAL_PRICE)
                .setDone(NOT_DONE).setRoom(room)
                .setLastDoneDate(LAST_DONE_DATE)
                .setPreviousLastDoneDate(PREVIOUS_LAST_DONE_DATE)
                .setLastDoneUserId(LAST_DONE_USER_ID)
                .setPreviousLastDoneUserId(PREVIOUS_LAST_DONE_USER_ID)
                .setRepetitionRate(REPETITION_RATE)
                .setBeginPeriodDate(BEGIN_PERIOD_DATE)
                .setLastDonePrice(LAST_DONE_PRICE)
                .setId(TASK_ID).createTask();
        notDoneTask.setCurrentPrice(CURRENT_PRICE);
        TaskEntity notDoneTaskEntity = notDoneTask.toEntity();
        when(taskRepository.getTaskByIdAndRoom_House(TASK_ID, house)).thenReturn(notDoneTaskEntity);
        notDoneTask.setLastDonePrice(CURRENT_PRICE);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(any())).thenReturn(notDoneTask);
        when(taskRepository.save(any())).thenAnswer(returnsFirstArg());
        when(user.getId()).thenReturn(USER_ID);

        //when
        Task actual = systemUnderTest.setTaskDone(TASK_ID, house, DONE, user);

        //then
        assertThat(actual)
                .extracting(
                        Task::isDone,
                        Task::getInitialPrice,
                        Task::getName,
                        Task::getRoom,
                        Task::getId,
                        Task::getLastDoneDate,
                        Task::getPreviousLastDoneDate,
                        Task::getLastDoneUserId,
                        Task::getPreviousLastDoneUserId,
                        Task::getRepetitionRate,
                        Task::getBeginPeriodDate,
                        Task::getLastDonePrice)
                .contains(
                        DONE,
                        notDoneTask.getInitialPrice(),
                        notDoneTask.getName(),
                        notDoneTask.getRoom(),
                        notDoneTask.getId(),
                        LocalDate.now(),
                        USER_ID,
                        notDoneTask.getLastDoneDate(),
                        notDoneTask.getLastDoneUserId(),
                        notDoneTask.getRepetitionRate(),
                        notDoneTask.getBeginPeriodDate(),
                        notDoneTask.getCurrentPrice().get()
                );

    }

    @Test
    void shouldSetTaskNotDone() {
        //given
        TaskEntity doneTaskEntity = new TaskEntityBuilder().setId(TASK_ID).setName(TASK_NAME).setInitialPrice(INITIAL_PRICE).setDone(DONE).setRoom(room).setLastDoneDate(LAST_DONE_DATE).setPreviousLastDoneDate(PREVIOUS_LAST_DONE_DATE).setLastDoneUserId(USER_ID).setPreviousLastDoneUserId(PREVIOUS_LAST_DONE_USER_ID).setRepetitionRate(REPETITION_RATE).setBeginPeriodDate(BEGIN_PERIOD_DATE).setLastDonePrice(LAST_DONE_PRICE).createTaskEntity();

        Task expectedTask = new TaskBuilder()
                .setId(TASK_ID)
                .setName(TASK_NAME)
                .setInitialPrice(INITIAL_PRICE)
                .setDone(NOT_DONE)
                .setRoom(room)
                .setLastDoneDate(PREVIOUS_LAST_DONE_DATE)
                .setPreviousLastDoneDate(PREVIOUS_LAST_DONE_DATE)
                .setLastDoneUserId(PREVIOUS_LAST_DONE_USER_ID)
                .setPreviousLastDoneUserId(PREVIOUS_LAST_DONE_USER_ID)
                .setRepetitionRate(REPETITION_RATE)
                .setBeginPeriodDate(BEGIN_PERIOD_DATE)
                .setLastDonePrice(LAST_DONE_PRICE)
                .createTask();

        when(taskRepository.getTaskByIdAndRoom_House(TASK_ID, house)).thenReturn(doneTaskEntity);
        when(taskPriceUpdaterService.getOneTaskWithUpdatedPrice(any())).thenAnswer(returnsFirstArg());
        when(taskRepository.save(any())).thenAnswer(returnsFirstArg());
        when(user.getId()).thenReturn(USER_ID);

        //when
        Task actual = systemUnderTest.setTaskDone(TASK_ID, house, NOT_DONE, user);

        //then
        assertThat(actual)
                .extracting(
                        Task::isDone,
                        Task::getInitialPrice,
                        Task::getName,
                        Task::getRoom,
                        Task::getId,
                        Task::getLastDoneDate,
                        Task::getPreviousLastDoneDate,
                        Task::getLastDoneUserId,
                        Task::getPreviousLastDoneUserId,
                        Task::getRepetitionRate,
                        Task::getBeginPeriodDate,
                        Task::getLastDonePrice)
                .contains(
                        expectedTask.isDone(),
                        expectedTask.getInitialPrice(),
                        expectedTask.getName(),
                        expectedTask.getRoom(),
                        expectedTask.getId(),
                        expectedTask.getLastDoneDate(),
                        expectedTask.getLastDoneUserId(),
                        expectedTask.getPreviousLastDoneDate(),
                        expectedTask.getPreviousLastDoneUserId(),
                        expectedTask.getRepetitionRate(),
                        expectedTask.getBeginPeriodDate(),
                        expectedTask.getLastDonePrice()
                );

        verify(taskRepository, times(1)).save(expectedTask.toEntity());
    }

    @Test
    void shouldResetTasks() {
        // given
        LocalDate now = LocalDate.now();
        List<TaskEntity> taskEntities = List.of(
                getWeeklyTaskEntity(now, 1L, 9, 7, true),
                getWeeklyTaskEntity(now, 2L, 8, 6, true),
                getWeeklyTaskEntity(now, 3L, 10, 8, true),
                getWeeklyTaskEntity(now, 4L, 6, 4, true),
                getWeeklyTaskEntity(now, 5L, 6, 4, false),
                getWeeklyTaskEntity(now, 6L, 9, 0, true),
                getWeeklyTaskEntity(now, 7L, 0, 0, true),
                getWeeklyTaskEntity(now, 8L, 0, 7, true),
                getWeeklyTaskEntity(now, 9L, 0, 6, true),
                getWeeklyTaskEntity(now, 10L, 0, 8, true),
                getWeeklyTaskEntity(now, 11L, 6, 0, true),
                getWeeklyTaskEntity(now, 12L, 7, 0, true),
                getWeeklyTaskEntity(now, 13L, 8, 0, true)
        );

        when(taskRepository.findAll()).thenReturn(taskEntities);

        ArgumentCaptor<Iterable<TaskEntity>> captor = ArgumentCaptor.forClass(ArrayList.class);

        //when
        systemUnderTest.undoneTasksAfterTheirPeriod();

        //then
        verify(taskRepository).saveAll(captor.capture());
        List<TaskEntity> savedTasks = (List<TaskEntity>) captor.getValue();


        shouldBeReseted(now, savedTasks, 1L);
        shouldNotBeReseted(savedTasks, 2L);
        shouldBeReseted(now, savedTasks, 3L);
        shouldNotBeReseted(savedTasks, 4L);
        shouldNotBeReseted(savedTasks, 5L);
        shouldNotBeReseted(savedTasks, 6L);
        shouldNotBeReseted(savedTasks, 7L);
        shouldBeReseted(now, savedTasks, 8L);
        shouldNotBeReseted(savedTasks, 9L);
        shouldBeReseted(now, savedTasks, 10L);
        shouldNotBeReseted(savedTasks, 11L);
        shouldNotBeReseted(savedTasks, 12L);
        shouldNotBeReseted(savedTasks, 13L);

    }

    private void shouldBeReseted(LocalDate now, List<TaskEntity> savedTasks, long id) {
        Optional<TaskEntity> expectedTask = savedTasks.stream().filter(t -> t.getId() == id).findFirst();
        assertThat(expectedTask).isPresent();
        assertThat(expectedTask.get())
                .extracting(
                        TaskEntity::isDone,
                        TaskEntity::getBeginPeriodDate
                )
                .containsExactly(
                        false,
                        now);
    }

    private void shouldNotBeReseted(List<TaskEntity> savedTasks, long id) {
        Optional<TaskEntity> expectedTask = savedTasks.stream().filter(t -> t.getId() == id).findFirst();
        assertThat(expectedTask).isEmpty();
    }

    private TaskEntity getWeeklyTaskEntity(LocalDate now, long taskId, int bp, int ldd, boolean done) {
        return taskBuilder
                .setRepetitionRate(Period.ofDays(7))
                .setId(taskId)
                .setBeginPeriodDate(now.minusDays(bp))
                .setLastDoneDate(now.minusDays(ldd))
                .setDone(done)
                .createTask()
                .toEntity();
    }
}