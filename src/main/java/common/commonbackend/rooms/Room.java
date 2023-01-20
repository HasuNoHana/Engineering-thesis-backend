package common.commonbackend.rooms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.commonbackend.houses.HouseEntity;
import common.commonbackend.tasks.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Set<TaskEntity> tasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "HOUSE_ID", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @Setter
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

    public static Room fromDto(RoomDTO roomDTO) {
        return new Room(
                roomDTO.getId(),
                roomDTO.getName(),
                roomDTO.getImage(),
                null); //TODO przemyslec to
    }

    void updateFromDTO(RoomDTO roomDTO) {
        this.name = roomDTO.getName();
        this.image = roomDTO.getImage();
    }

    public RoomDTO toDto(int notDoneTasks) {
        return new RoomDTO(id, name, image, notDoneTasks);
    }
}
