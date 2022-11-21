package common.commonbackend.dto;

import lombok.Data;

@Data
public class TaskDTO {
    String name;
    int price;
    boolean done;
    RoomDTO room;
}
