package common.commonbackend.rooms;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class RoomDTO {
    long id;
    String name;
    String image;
    int tasksNotDone;
}
