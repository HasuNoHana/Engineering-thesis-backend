package common.commonbackend.tasks;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@RequiredArgsConstructor
public class TaskDTO {
    Long id;
    String name;
    long initialPrice;
    Long currentPrice;
    boolean done;
    long roomId;
    LocalDate lastDoneDate;
    int repetitionRateInDays;
}
