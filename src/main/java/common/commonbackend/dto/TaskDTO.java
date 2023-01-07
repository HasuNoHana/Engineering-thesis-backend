package common.commonbackend.dto;

import lombok.Data;

@Data
public class TaskDTO {
    String name;
    int price;
    boolean done;
    long roomId;

    public TaskDTO(String name, int price, boolean done, long roomId) {
        this.name = name;
        this.price = price;
        this.done = done;
        this.roomId = roomId;
    }
}
