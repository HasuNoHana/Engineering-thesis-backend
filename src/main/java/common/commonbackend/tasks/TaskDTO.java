package common.commonbackend.tasks;

import common.commonbackend.rooms.RoomDTO;
import lombok.Value;

import java.time.LocalDate;

@Value
class TaskDTO {
    Long id;
    String name;
    long initialPrice;
    Long currentPrice;
    boolean done;
    RoomDTO room;
    LocalDate lastDoneDate;
    int repetitionRateInDays;

    TaskDTO(Long id, String name, long initialPrice, Long currentPrice, boolean done, RoomDTO room, LocalDate lastDoneDate, int repetitionRateInDays) { //NOSONAR
        this.id = id;
        this.name = name;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.done = done;
        this.room = room;
        this.lastDoneDate = lastDoneDate;
        this.repetitionRateInDays = repetitionRateInDays;
    }
}
