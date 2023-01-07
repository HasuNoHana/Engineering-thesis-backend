package common.commonbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.commonbackend.dto.RoomDTO;
import common.commonbackend.house.HouseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "ROOM")
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IMAGE")
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Task> tasks;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "house_entity_id", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private HouseEntity house;

    public Room(String name, String image, HouseEntity house) {
        this.name = name;
        this.image = image;
        this.house = house;
    }

    public Room(Long id, String name, String image, HouseEntity house) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.house = house;
    }

    public static Room fromRoomDto(RoomDTO roomDTO) {
        Set<Task> tasks = new HashSet<>();
        return new Room(
                null,
                roomDTO.getName(),
                roomDTO.getImage(),
                tasks,
                roomDTO.getHouse()
        );
    }
}
