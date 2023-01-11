package common.commonbackend.user;

import common.commonbackend.house.HouseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static common.commonbackend.user.HouseBuddy.getDefaultHouseBuddy;

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
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "HOUSE_BUDDY_ID", referencedColumnName = "id")
    private HouseBuddy houseBuddy;

    public User(String username, String password, HouseEntity house) {
        this.username = username;
        this.password = password;
        this.houseBuddy = getDefaultHouseBuddy(house);
    }
}
