package common.commonbackend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Getter
@Table(name = "ROOM")
public class Room {
    @Column(name = "NAME")
    private final String name;
    @Column(name = "IMAGE")
    private final String image;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    public Room(Long id, String name, String image) {
        this.id = id;
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
