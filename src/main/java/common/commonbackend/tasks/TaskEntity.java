package common.commonbackend.tasks;

import common.commonbackend.rooms.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(name = "TASK")
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private final String name;

    @Column(name = "PRICE")
    private final long initialPrice;

    @Column(name = "DONE")
    private final boolean done;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private final Room room;

    @Column(name = "LAST_DONE_DATE")
    private LocalDate lastDoneDate = LocalDate.now();

    @Column(name = "PREVIOUS_LAST_DONE_DATE")
    private LocalDate previousLastDoneDate = LocalDate.now();

    @Column(name = "LAST_DONE_USER_ID")
    private long lastDoneUserId;

    @Column(name = "PREVIOUS_LAST_DONE_USER_ID")
    private long previousLastDoneUserId;

    @Column(name = "REPETITION_RATE")
    private Period repetitionRate = Period.ofDays(7);

    @Column(name = "BEGIN_PERIOD_DATE")
    private LocalDate beginPeriodDate = LocalDate.now();

    @Column(name = "LAST_DONE_PRICE")
    private long lastDonePrice = 0;
}
