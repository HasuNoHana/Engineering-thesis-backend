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

    @Column(name = "INITIAL_PRICE")
    private long initialPrice;

    @Column(name = "DONE")
    private boolean done;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private Room room;


    @Column(name = "LAST_DONE_DATE")
    private LocalDate lastDoneDate = LocalDate.now();

    @Column(name = "PREVIOUS_LAST_DONE_DATE")
    private LocalDate previousLastDoneDate = LocalDate.now();

    @Column(name = "LAST_DONE_USER_ID")
    private long lastDoneUserId;

    @Column(name = "PREVIOUS_LAST_DONE_USER_ID")
    private long previousLastDoneUserId;

    @Column(name = "REPETITION_RATE")
    private Period repetitionRate = Period.ofDays(1);


//    public Task(Long id, String name, long initialPrice, boolean done, Room room) {
//        this.id = id;
//        this.name = name;
//        this.initialPrice = initialPrice;
//        this.done = done;
//        this.room = room;
//    }

//    public Task(String name, long initialPrice, boolean done, Room room) {
//        this.name = name;
//        this.initialPrice = initialPrice;
//        this.done = done;
//        this.room = room;
//    }

    public Task(String name, long initialPrice, boolean done, Room room, long lastDoneUserId, Period repetitionRate) {
        this.name = name;
        this.initialPrice = initialPrice;
        this.done = done;
        this.room = room;
        this.lastDoneDate = LocalDate.now();
        this.previousLastDoneDate = LocalDate.now();
        this.lastDoneUserId = lastDoneUserId;
        this.previousLastDoneUserId = lastDoneUserId;
        this.repetitionRate = repetitionRate;
    }

    public Task(Long id, String name, long initialPrice, boolean done, Room room, long lastDoneUserId, Period repetitionRate) {
        this.id = id;
        this.name = name;
        this.initialPrice = initialPrice;
        this.done = done;
        this.room = room;
        this.lastDoneDate = LocalDate.now();
        this.previousLastDoneDate = LocalDate.now();
        this.lastDoneUserId = lastDoneUserId;
        this.previousLastDoneUserId = lastDoneUserId;
        this.repetitionRate = repetitionRate;
    }

    public Task(String name, long initialPrice, long currentPrice, boolean done, Room room, LocalDate lastDoneDate, LocalDate previousLastDoneDate, long lastDoneUserId, long previousLastDoneUserId, Period repetitionRate) {
        this.name = name;
        this.initialPrice = initialPrice;
        this.done = done;
        this.room = room;
        this.lastDoneDate = lastDoneDate;
        this.previousLastDoneDate = previousLastDoneDate;
        this.lastDoneUserId = lastDoneUserId;
        this.previousLastDoneUserId = previousLastDoneUserId;
        this.repetitionRate = repetitionRate;
    }

//    private Task(Long id, String name, long initialPrice, long currentPrice, boolean done, Room room,
//                 LocalDate lastDoneDate, LocalDate previousLastDoneDate, long lastDoneUserId,
//                 long previousLastDoneUserId, Period repetitionRate) {
//        this.id = id;
//        this.name = name;
//        this.initialPrice = initialPrice;
//        this.currentPrice = currentPrice;
//        this.done = done;
//        this.room = room;
//        this.lastDoneDate = lastDoneDate;
//        this.previousLastDoneDate = previousLastDoneDate;
//        this.lastDoneUserId = lastDoneUserId;
//        this.previousLastDoneUserId = previousLastDoneUserId;
//        this.repetitionRate = repetitionRate;
//    }

    public static Task fromDto(TaskDTO taskDTO, Room room, long currentUserId) {
        return new Task(
                taskDTO.getName(),
                taskDTO.getInitialPrice(), // TODO tu jest bug z logigki biznesowej. Actual price jest przypisywany do initial price
                taskDTO.isDone(),
                room,
                currentUserId,
                Period.ofDays(taskDTO.getRepetitionRate())
        );
    }

    public Task getNewTaskWithUpdatedPrice(long newPrice) {
        return new Task(this.id, this.name, this.initialPrice, newPrice, this.done, this.room,
                LocalDate.now(), this.lastDoneDate, this.lastDoneUserId, this.previousLastDoneUserId,
                this.repetitionRate);
    }

    public void updateFromDto(TaskDTO updatedTask, Room room) {
        this.name = updatedTask.getName();
        this.initialPrice = updatedTask.getInitialPrice();
        this.done = updatedTask.isDone();
        this.room = room;
        this.repetitionRate = Period.ofDays(updatedTask.getRepetitionRate());
    }
}
