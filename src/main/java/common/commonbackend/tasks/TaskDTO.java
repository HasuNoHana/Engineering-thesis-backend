package common.commonbackend.tasks;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class TaskDTO {
    String name;
    long price; //TODO to jest intial prcie czy actual
    boolean done;
    long roomId;
}
