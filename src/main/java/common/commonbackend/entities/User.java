package common.commonbackend.entities;

import common.commonbackend.house.HouseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@NoArgsConstructor(force = true)
@Entity
@Getter
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private final String username;

    @Column(name = "PASSWORD")
    private final String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "house_entity_id", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private HouseEntity house;

    public User(String username, String password, HouseEntity house) {
        this.username = username;
        this.password = password;
        this.house = house;
    }
}
