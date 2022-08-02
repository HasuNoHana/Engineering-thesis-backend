package common.commonbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(force = true)
@Entity
@Getter
@Table(name = "ROOM")
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
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public Room(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Room(String name, String image) {
        this.name = name;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && Objects.equals(image, room.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image);
    }
}
