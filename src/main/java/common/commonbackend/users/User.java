package common.commonbackend.users;

import common.commonbackend.users.house_buddy.HouseBuddy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String username;
    @Setter
    @Column(name = "PASSWORD")
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "HOUSE_BUDDY_ID", referencedColumnName = "id")
    private HouseBuddy houseBuddy;

    public User(String username, String password, HouseBuddy houseBuddy) {
        this.username = username;
        this.password = password;
        this.houseBuddy = houseBuddy;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}
