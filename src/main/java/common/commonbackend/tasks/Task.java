package common.commonbackend.tasks;

import common.commonbackend.rooms.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor(force = true)
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "TASK") // TODO task powinien byc rozdzielony na task DTO ktory kominukuje sie z baza i task ktory jest wykorzystywany w biznesowej czesci do zmiany price
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private long initialPrice;

    @Column(name = "DONE")
    private boolean done;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;


    @Column(name = "LAST_DONE_DATE")
    private LocalDate lastDoneDate = LocalDate.now(); //TODO add persistence

    @Column(name = "REPETITION_RATE")
    private Period repetitionRate = Period.ofDays(1); //TODO add persistence


    public Task(Long id, String name, long initialPrice, boolean done, Room room) {
        this.id = id;
        this.name = name;
        this.initialPrice = initialPrice; //TODO wywalić to
        this.done = done;
        this.room = room;
    }

    public Task(String name, long initialPrice, boolean done, Room room) { // NOSONAR TODO remove this constructor
        this.name = name;
        this.initialPrice = initialPrice;//TODO wywalić to
        this.done = done;
        this.room = room;
    }

    public static Task fromDto(TaskDTO taskDTO, Room room) {
        return new Task(
                taskDTO.getName(),
                taskDTO.getPrice(), // TODO tu jest bug z logigki biznesowej. Actual price jest przypisywany do initial price
                taskDTO.isDone(),
                room
        );
    }

    public Task getNewTaskWithUpdatedPrice(long newPrice) {
        return new Task(this.id, this.name, newPrice, this.done, this.room);
    }

    public void updateFromDto(TaskDTO updatedTask, Room room) {
        this.name = updatedTask.getName();
        this.initialPrice = updatedTask.getPrice();
        this.done = updatedTask.isDone();
        this.room = room;
    }
}
