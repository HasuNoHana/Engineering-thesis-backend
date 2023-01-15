package common.commonbackend.tasks;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class TaskDTO {
    Long id;
    String name;
    long initialPrice;
    boolean done;
    long roomId;
}
