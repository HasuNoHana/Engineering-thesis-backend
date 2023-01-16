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

    private final Period repetitionRate;

    Task(Long id, String name, long initialPrice, boolean done, Room room, LocalDate lastDoneDate, Period repetitionRate) {
        this.id = id;
        this.name = name;
        this.initialPrice = initialPrice;
        this.done = done;
        this.room = room;
        this.lastDoneDate = lastDoneDate;
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
                    .createTask();
        } else {
            return taskBuilder
                    .setId(taskDTO.getId())
                    .setDone(taskDTO.isDone())
                    .setLastDoneDate(taskDTO.getLastDoneDate())
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
                .setRepetitionRateInDays(repetitionRate.getDays())
                .createTaskDTO();
    }

    TaskEntity toEntity() {
        return new TaskEntity(id, name, initialPrice, done, room, lastDoneDate, repetitionRate);
    }

    public Task setCurrentPrice(long newPrice) {
        this.currentPrice = Optional.of(newPrice);
        return this;
    }
}
