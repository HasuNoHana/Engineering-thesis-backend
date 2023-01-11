package common.commonbackend.user;

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
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInformation userInformation;


    public User(String username, String password, UserInformation userInformation) {
        this.username = username;
        this.password = password;
        this.userInformation = userInformation;
    }
}
