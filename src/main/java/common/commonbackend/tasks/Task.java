package common.commonbackend.tasks;

import common.commonbackend.rooms.Room;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class Task {
    private final Long id;
    private final String name;
    private final long initialPrice;
    private Optional<Long> currentPrice = Optional.empty();
    @Setter // TODO rethink if this is needed
    private boolean done;
    @Setter // TODO rethink if this is needed
    private Room room;

    private final LocalDate lastDoneDate;

    private final LocalDate previousLastDoneDate;

    private final long lastDoneUserId;

    private final long previousLastDoneUserId;

    private final Period repetitionRate;

    Task(Long id, String name, long initialPrice, boolean done, Room room, LocalDate lastDoneDate,//NOSONAR
         LocalDate previousLastDoneDate, long lastDoneUserId, long previousLastDoneUserId, Period repetitionRate) { //NOSONAR
        this.id = id;
        this.name = name;
        this.initialPrice = initialPrice;
        this.done = done;
        this.room = room;
        this.lastDoneDate = lastDoneDate;
        this.previousLastDoneDate = previousLastDoneDate;
        this.lastDoneUserId = lastDoneUserId;
        this.previousLastDoneUserId = previousLastDoneUserId;
        this.repetitionRate = repetitionRate;
    }

    static Task fromEntity(TaskEntity taskEntity) {
        return new TaskBuilder()
                .setId(taskEntity.getId())
                .setName(taskEntity.getName())
                .setInitialPrice(taskEntity.getInitialPrice())
                .setDone(taskEntity.isDone())
                .setRoom(taskEntity.getRoom())
                .setLastDoneDate(taskEntity.getLastDoneDate())
                .setPreviousLastDoneDate(taskEntity.getPreviousLastDoneDate())
                .setLastDoneUserId(taskEntity.getLastDoneUserId())
                .setPreviousLastDoneUserId(taskEntity.getPreviousLastDoneUserId())
                .setRepetitionRate(taskEntity.getRepetitionRate())
                .createTask();
    }

    static Task fromDto(TaskDTO taskDTO) {
        TaskBuilder taskBuilder = new TaskBuilder()
                .setName(taskDTO.getName())
                .setInitialPrice(taskDTO.getInitialPrice())
                .setRoom(Room.fromDto(taskDTO.getRoom()))
                .setRepetitionRate(Period.ofDays(taskDTO.getRepetitionRateInDays()));

        if (taskDTO.getId() == null) {
            return taskBuilder
                    .setId(null)
                    .setDone(false)
                    .setLastDoneDate(LocalDate.now())
                    .setPreviousLastDoneDate(LocalDate.now())
                    .setLastDoneUserId(0)
                    .setPreviousLastDoneUserId(0)
                    .createTask();
        } else {
            return taskBuilder
                    .setId(taskDTO.getId())
                    .setDone(taskDTO.isDone())
                    .setLastDoneDate(taskDTO.getLastDoneDate())
                    .setPreviousLastDoneDate(taskDTO.getPreviousLastDoneDate())
                    .setLastDoneUserId(taskDTO.getLastDoneUserId())
                    .setPreviousLastDoneUserId(taskDTO.getPreviousLastDoneUserId())
                    .createTask();
        }
    }


    TaskDTO toDto() {
        return new TaskDTOBuilder()
                .setId(id)
                .setName(name)
                .setInitialPrice(initialPrice)
                .setCurrentPrice(currentPrice.orElse(null))
                .setDone(done).setRoom(room.toDto())
                .setLastDoneDate(lastDoneDate)
                .setPreviousLastDoneDate(previousLastDoneDate)
                .setLastDoneUserId(lastDoneUserId)
                .setPreviousLastDoneUserId(previousLastDoneUserId)
                .setRepetitionRateInDays(repetitionRate.getDays())
                .createTaskDTO();
    }

    TaskEntity toEntity() {
        return new TaskEntity(id, name, initialPrice, done, room, lastDoneDate,
                previousLastDoneDate, lastDoneUserId, previousLastDoneUserId, repetitionRate);
    }

    public Task setCurrentPrice(long newPrice) {
        this.currentPrice = Optional.of(newPrice);
        return this;
    }
}
