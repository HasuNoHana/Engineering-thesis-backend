package common.commonbackend.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.commonbackend.houses.HouseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "HOUSE_BUDDY")
@Getter
@NoArgsConstructor
public class HouseBuddy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIREWOOD_STACK_SIZE")
    private Long firewoodStackSize;

    @Column(name = "WEEKLY_FIREWOOD_CONTRIBUTION")
    private Long weeklyFirewoodContribution;

    @Column(name = "AVATAR_IMAGE_URL")
    private String avatarImageUrl;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "HOUSE_ID", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private HouseEntity house;

    @JsonIgnore
    @OneToOne(mappedBy = "houseBuddy")
    private User user;

    public HouseBuddy(Long firewoodStackSize, Long weeklyPointsContribution, String avatarImageUrl, HouseEntity house) {
        this.firewoodStackSize = firewoodStackSize;
        this.weeklyFirewoodContribution = weeklyPointsContribution;
        this.avatarImageUrl = avatarImageUrl;
        this.house = house;
    }

    public HouseBuddy(Long firewoodStackSize, Long weeklyPointsContribution, String avatarImageUrl, HouseEntity house, User user) {
        this.firewoodStackSize = firewoodStackSize;
        this.weeklyFirewoodContribution = weeklyPointsContribution;
        this.avatarImageUrl = avatarImageUrl;
        this.house = house;
        this.user = user;
    }
}