package common.commonbackend.tasks;

import common.commonbackend.rooms.Room;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Task {
    private Long id;
    private String name;
    private long initialPrice;
    @Setter // TODO rethink if this is needed
    private boolean done;
    @Setter // TODO rethink if this is needed
    private Room room;

    static Task fromEntity(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getName(),
                taskEntity.getInitialPrice(),
                taskEntity.isDone(),
                taskEntity.getRoom());
    }

    static Task fromDTOAndRoom(TaskDTO taskDTO) {
        return new Task(taskDTO.getId(),
                taskDTO.getName(),
                taskDTO.getInitialPrice(),
                taskDTO.isDone(),
                null); //TODO rethink this?
    }


    TaskDTO toDto() {
        return new TaskDTO(id, name, initialPrice, done, room.getId());
    }

    TaskEntity toEntity() {
        return new TaskEntity(id, name, initialPrice, done, room);
    }

}
