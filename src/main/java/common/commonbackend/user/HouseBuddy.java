package common.commonbackend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.commonbackend.house.HouseEntity;
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

    public static final long DEFAULT_FIREWOOD_STACK_SIZE = 0L;
    public static final long DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION = 100L;
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg";


    public HouseBuddy(Long firewoodStackSize, Long weeklyPointsContribution, String avatarImageUrl, HouseEntity house) {
        this.firewoodStackSize = firewoodStackSize;
        this.weeklyFirewoodContribution = weeklyPointsContribution;
        this.avatarImageUrl = avatarImageUrl;
        this.house = house;
    }

    static HouseBuddy getDefaultHouseBuddy(HouseEntity house) {
        return new HouseBuddy(
                DEFAULT_FIREWOOD_STACK_SIZE,
                DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION,
                DEFAULT_IMAGE,
                house);
    }
}