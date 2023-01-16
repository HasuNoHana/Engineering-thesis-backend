package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    private static final Period REPETITION_RATE = Period.ofDays(1);
    private static final LocalDate LAST_DONE_DATE = LocalDate.now();
    private static final HouseEntity house = new HouseEntity(); //TODO room and house also should be moved to Entity and domain object. We should test here whole mapping to check if not detalis are misisng
    private static final Room room = new Room(2L, "RoomName", "imageUrl", house);
    private static final RoomDTO roomDTO = new RoomDTO(2L, "RoomName", "imageUrl");

    private static final String TASK_NAME = "TaskName";
    private static final long INITIAL_PRICE = 10L;
    private static final long ID = 1L;
    private final static TaskBuilder taskBuilder = new TaskBuilder()
            .setId(ID)
            .setName(TASK_NAME)
            .setInitialPrice(INITIAL_PRICE)
            .setDone(false)
            .setRoom(room)
            .setLastDoneDate(LAST_DONE_DATE)
            .setRepetitionRate(REPETITION_RATE);
    private static final TaskEntity taskEntity = new TaskEntity(ID, TASK_NAME, 10, false,
            room, LAST_DONE_DATE, REPETITION_RATE);

    private static final TaskDTOBuilder taskDTOBuilder = new TaskDTOBuilder()
            .setId(ID)
            .setName(TASK_NAME)
            .setInitialPrice(INITIAL_PRICE)
            .setDone(false)
            .setRoom(roomDTO)
            .setLastDoneDate(LAST_DONE_DATE)
            .setRepetitionRateInDays(REPETITION_RATE.getDays());
    private static final TaskDTO taskDTOWithCurrentPrice = taskDTOBuilder.setCurrentPrice(20L).createTaskDTO();
    private static final TaskDTO taskDTOWithoutCurrentPrice = taskDTOBuilder.setCurrentPrice(null).createTaskDTO();


    @Test
    void shouldMapToEntity() {
        //given
        Task taskWithoutCurrentPrice = taskBuilder.createTask();

        //when
        TaskEntity actualTaskEntity = taskWithoutCurrentPrice.toEntity();

        //then
        assertThat(actualTaskEntity).isEqualTo(taskEntity);
    }

    @Test
    void shouldMapFromEntity() {
        //given
        Task expected = taskBuilder.createTask();

        //when
        Task actualTask = Task.fromEntity(taskEntity);

        //then
        assertThat(actualTask).isEqualTo(expected);
    }

    @Test
    void shouldMapToDtoWithCurrentPrice() {
        //given
        Task taskWithCurrentPrice = taskBuilder.createTask();
        taskWithCurrentPrice.setCurrentPrice(20L);

        //when
        TaskDTO actualTaskDTO = taskWithCurrentPrice.toDto();

        //then
        assertEquals(taskDTOWithCurrentPrice, actualTaskDTO);
    }

    @Test
    void shouldMapToDtoWithoutCurrentPrice() {
        //given
        Task taskWithoutCurrentPrice = taskBuilder.createTask();

        //when
        TaskDTO actualTaskDTO = taskWithoutCurrentPrice.toDto();

        //then
        assertThat(actualTaskDTO).isEqualTo(taskDTOWithoutCurrentPrice);
    }

    @ParameterizedTest
    @MethodSource("provideStreamForUpdateAlgorithm")
    void shouldMapFromDto(TaskDTO givenTaskDTO, Task expectedTask) {
        //when
        Task actualTask = Task.fromDto(givenTaskDTO);

        //then
        assertThat(actualTask)
                .extracting(
                        Task::getId,
                        Task::getName,
                        Task::getInitialPrice,
                        Task::isDone,
                        Task::getLastDoneDate,
                        Task::getRepetitionRate,
                        task -> task.getRoom().getId()
                ).containsExactly(
                        expectedTask.getId(),
                        expectedTask.getName(),
                        expectedTask.getInitialPrice(),
                        expectedTask.isDone(),
                        expectedTask.getLastDoneDate(),
                        expectedTask.getRepetitionRate(),
                        expectedTask.getRoom().getId()
                );
    }

    private static Stream<Arguments> provideStreamForUpdateAlgorithm() {
        return Stream.of(
                Arguments.of(new TaskDTOBuilder()
                                .setId(ID)
                                .setName(TASK_NAME)
                                .setInitialPrice(INITIAL_PRICE)
                                .setDone(false)
                                .setRoom(roomDTO)
                                .setLastDoneDate(LAST_DONE_DATE)
                                .setRepetitionRateInDays(REPETITION_RATE.getDays())
                                .createTaskDTO(),

                        new TaskBuilder()
                                .setId(ID)
                                .setName(TASK_NAME)
                                .setInitialPrice(INITIAL_PRICE)
                                .setDone(false)
                                .setRoom(room)
                                .setLastDoneDate(LAST_DONE_DATE)
                                .setRepetitionRate(REPETITION_RATE)
                                .createTask()),

                Arguments.of(new TaskDTOBuilder()
                                .setName(TASK_NAME)
                                .setInitialPrice(INITIAL_PRICE)
                                .setRoom(roomDTO)
                                .setRepetitionRateInDays(REPETITION_RATE.getDays())
                                .createTaskDTO(),

                        new TaskBuilder()
                                .setId(null)
                                .setName(TASK_NAME)
                                .setInitialPrice(INITIAL_PRICE)
                                .setDone(false)
                                .setRoom(room)
                                .setLastDoneDate(LocalDate.now())
                                .setRepetitionRate(REPETITION_RATE)
                                .createTask())
        );
    }

}