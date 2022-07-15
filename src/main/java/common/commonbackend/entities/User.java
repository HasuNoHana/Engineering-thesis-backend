package common.commonbackend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
