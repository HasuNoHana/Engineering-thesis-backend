package common.commonbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.commonbackend.dto.RoomDTO;
import common.commonbackend.house.HouseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Table(name = "ROOM")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private final String name;

    @Column(name = "IMAGE")
    private final String image;

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Task> tasks;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "house_entity_id", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private final HouseEntity house;

    public static Room fromRoomDto(RoomDTO roomDTO) {
        return new Room(
                roomDTO.getName(),
                roomDTO.getImage(),
                roomDTO.getHouse()
        );
    }
}
