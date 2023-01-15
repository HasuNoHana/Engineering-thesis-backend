package common.commonbackend.tasks;

import common.commonbackend.rooms.Room;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(force = true)
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "TASK")
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

//    @Column(name = "LAST_DONE_DATE")
//    private LocalDate lastDoneDate = LocalDate.now();
//
//    @Column(name = "REPETITION_RATE")
//    private Period repetitionRate = Period.ofDays(1);
}
