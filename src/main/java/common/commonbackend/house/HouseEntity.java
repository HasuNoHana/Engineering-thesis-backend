package common.commonbackend.house;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.commonbackend.entities.Room;
import common.commonbackend.entities.User;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class HouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "JOIN_CODE")
    private String joinCode;

    @JsonIgnore
    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
    }
}
