package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class UndoneTasksAfterPeriodSchedulerTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    HouseEntity house;
    private static final String TASK_NAME = "TaskName";
    private static final long ROOM_ID = 2L;
    private static final String ROOM_NAME = "Kuchnia";
    private static final String IMAGE_URL = "url";
    private static final long INITIAL_PRICE = 10;
    private static final LocalDate LAST_DONE_DATE = LocalDate.now();
    private static final long LAST_DONE_USER_ID = 1L;
    private static final LocalDate PREVIOUS_LAST_DONE_DATE = LocalDate.now().minusDays(3);
    private static final long PREVIOUS_LAST_DONE_USER_ID = 2L;
    private final Room room = new Room(ROOM_ID, ROOM_NAME, IMAGE_URL, house);

    private final TaskBuilder taskBuilder = new TaskBuilder()
            .setName(TASK_NAME)
            .setInitialPrice(INITIAL_PRICE)
            .setRoom(room)
            .setLastDoneDate(LAST_DONE_DATE)
            .setPreviousLastDoneDate(PREVIOUS_LAST_DONE_DATE)
            .setLastDoneUserId(LAST_DONE_USER_ID)
            .setPreviousLastDoneUserId(PREVIOUS_LAST_DONE_USER_ID);

    private UndoneTasksAfterPeriodScheduler systemUnderTest;


    @BeforeEach
    void setUp() {
        systemUnderTest = new UndoneTasksAfterPeriodScheduler(taskRepository);
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