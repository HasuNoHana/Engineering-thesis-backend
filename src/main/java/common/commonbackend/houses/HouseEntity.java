package common.commonbackend.houses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.commonbackend.rooms.Room;
import common.commonbackend.users.HouseBuddy;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "HOUSE")
public class HouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "JOIN_CODE")
    private String joinCode;

    @JsonIgnore
    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Room> rooms = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<HouseBuddy> houseBuddies = new HashSet<>();

    public void addHouseBuddy(HouseBuddy houseBuddy) {
        houseBuddies.add(houseBuddy);
    }
}
