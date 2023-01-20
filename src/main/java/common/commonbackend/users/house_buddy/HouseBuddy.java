package common.commonbackend.users.house_buddy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.commonbackend.houses.HouseEntity;
import common.commonbackend.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "HOUSE_BUDDY")
@Getter
@Setter
@NoArgsConstructor
public class HouseBuddy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CURRENT_POINTS")
    private Long currentPoints;

    @Column(name = "WEEKLY_CONTRIBUTION")
    private Long weeklyContribution;

    @Column(name = "AVATAR_IMAGE_URL")
    private String avatarImageUrl;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "HOUSE_ID", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private HouseEntity house;

    @JsonIgnore
    @OneToOne(mappedBy = "houseBuddy")
    private User user;

    public HouseBuddy(Long currentPoints, Long weeklyPointsContribution, String avatarImageUrl, HouseEntity house) {
        this.currentPoints = currentPoints;
        this.weeklyContribution = weeklyPointsContribution;
        this.avatarImageUrl = avatarImageUrl;
        this.house = house;
    }

    public HouseBuddy(Long currentPoints, Long weeklyPointsContribution, String avatarImageUrl, HouseEntity house, User user) {
        this.currentPoints = currentPoints;
        this.weeklyContribution = weeklyPointsContribution;
        this.avatarImageUrl = avatarImageUrl;
        this.house = house;
        this.user = user;
    }

    @Override
    public String toString() {
        return "HouseBuddy{" +
                "id=" + id +
                ", currentPoints=" + currentPoints +
                ", weeklyContribiution=" + weeklyContribution +
                ", user=" + user +
                '}';
    }
}