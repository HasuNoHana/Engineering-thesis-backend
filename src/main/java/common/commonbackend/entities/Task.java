package common.commonbackend.entities;

import common.commonbackend.dto.TaskDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor(force = true)
@Entity
@Getter
@Table(name = "TASK")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private final String name;

    @Column(name = "PRICE")
    private final int price;

    @Column(name = "DONE")
    private final boolean done;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private final Room room;


    public Task(Long id, String name, int price, boolean done, Room room) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.done = done;
        this.room = room;
    }

    public Task(String name, int price, boolean done, Room room) { // NOSONAR TODO remove this constructor
        this.name = name;
        this.price = price;
        this.done = done;
        this.room = room;
    }

    public static Task fromDto(TaskDTO taskDTO) {
        return new Task(
                taskDTO.getName(),
                taskDTO.getPrice(),
                taskDTO.isDone(),
                Room.fromRoomDto(taskDTO.getRoom())
        );
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
