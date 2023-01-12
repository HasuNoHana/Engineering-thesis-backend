package common.commonbackend.users;

import common.commonbackend.houses.HouseEntity;
import org.springframework.stereotype.Service;

@Service
public class HouseBuddyService {

    public static final long DEFAULT_FIREWOOD_STACK_SIZE = 0L;
    public static final long DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION = 100L;
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg";

    public HouseBuddy getDefaultHouseBuddy(HouseEntity house) {
        return new HouseBuddy(
                DEFAULT_FIREWOOD_STACK_SIZE,
                DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION,
                DEFAULT_IMAGE,
                house);
    }

    public UserDTO createUserDTOFromHouseBuddy(HouseBuddy houseBuddy) {
        return new UserDTO(
                houseBuddy.getUser().getId(),
                houseBuddy.getUser().getUsername(),
                houseBuddy.getFirewoodStackSize(),
                houseBuddy.getWeeklyFirewoodContribution(),
                houseBuddy.getAvatarImageUrl());
    }
}
