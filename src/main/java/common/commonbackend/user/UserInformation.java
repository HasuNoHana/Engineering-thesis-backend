package common.commonbackend.user;

import common.commonbackend.house.HouseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "USER_DETAILS")
@Getter
@NoArgsConstructor
public class UserInformation {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "POINTS")
    private Long points;

    @Column(name = "RANGE")
    private Long range;

    @Column(name = "IMAGE")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "house_entity_id", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private HouseEntity house;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserInformation(Long points, Long range, String image, HouseEntity house) {
        this.points = points;
        this.range = range;
        this.image = image;
        this.house = house;
    }
}