package common.commonbackend.tasks;

import common.commonbackend.rooms.Room;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Task {
    private Long id;
    private String name;
    private long initialPrice;
    private Optional<Long> currentPrice = Optional.empty();
    @Setter // TODO rethink if this is needed
    private boolean done;
    @Setter // TODO rethink if this is needed
    private Room room;

    private LocalDate lastDoneDate;

    private Period repetitionRate;

    public Task(Long id, String name, long initialPrice, boolean done, Room room, LocalDate lastDoneDate, Period repetitionRate) {
        this.id = id;
        this.name = name;
        this.initialPrice = initialPrice;
        this.done = done;
        this.room = room;
        this.lastDoneDate = lastDoneDate;
        this.repetitionRate = repetitionRate;
    }

    static Task fromEntity(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getName(),
                taskEntity.getInitialPrice(),
                taskEntity.isDone(),
                taskEntity.getRoom(),
                taskEntity.getLastDoneDate(),
                taskEntity.getRepetitionRate());
    }

    static Task fromDto(TaskDTO taskDTO) {
        Optional<Long> currentPrice = Optional.ofNullable(taskDTO.getCurrentPrice());
        return new Task(taskDTO.getId(),
                taskDTO.getName(),
                taskDTO.getInitialPrice(),
                currentPrice,
                taskDTO.isDone(),
                Room.fromDto(taskDTO.getRoom()),  //TODO rethink this?
                taskDTO.getLastDoneDate(),
                Period.ofDays(taskDTO.getRepetitionRateInDays()));
    }


    TaskDTO toDto() {
        return new TaskDTO(id, name, initialPrice, currentPrice.orElse(null), done, room.toDto(), lastDoneDate, repetitionRate.getDays());
    }

    TaskEntity toEntity() {
        return new TaskEntity(id, name, initialPrice, done, room, lastDoneDate, repetitionRate);
    }

    public Task setCurrentPrice(long newPrice) {
        this.currentPrice = Optional.of(newPrice);
        return this;
    }
}
