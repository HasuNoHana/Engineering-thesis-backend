package common.commonbackend.tasks;

import common.commonbackend.rooms.RoomDTO;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@RequiredArgsConstructor
class TaskDTO {
    Long id;
    String name;
    long initialPrice;
    Long currentPrice;
    boolean done;
    RoomDTO room;
    LocalDate lastDoneDate;
    int repetitionRateInDays;
}
