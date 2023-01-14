package common.commonbackend.tasks;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@RequiredArgsConstructor
public class TaskDTO {
    String name;
    long initialPrice;
    boolean done;
    long roomId;
    LocalDate lastDoneDate;
    long lastDoneUserId;
    int repetitionRate;

}
