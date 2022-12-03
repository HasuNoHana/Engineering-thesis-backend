package common.commonbackend.entities;

import common.commonbackend.dto.TaskDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor(force = true)
@Entity
@Getter
@Table(name = "TASK") // TODO task powinien byc rozdzielony na task DTO ktory kominukuje sie z baza i task ktory jest wykorzystywany w biznesowej czesci do zmiany price
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private final String name;

    @Column(name = "PRICE")
    private final long price;

    @Column(name = "DONE")
    private final boolean done;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private final Room room;


    @Column(name = "LAST_DONE_DATE")
    private final LocalDate lastDoneDate = LocalDate.now(); //TODO add persistence
    private final Period period = Period.ofDays(1); //TODO add persistence


    public Task(Long id, String name, int price, boolean done, Room room) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.done = done;
        this.room = room;
    }

    public Task(String name, long price, boolean done, Room room) { // NOSONAR TODO remove this constructor
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

    public Task getNewTaskWithUpdatedPrice(long newPrice) {
        return new Task(this.name, newPrice, this.done, this.room);
    }
}
