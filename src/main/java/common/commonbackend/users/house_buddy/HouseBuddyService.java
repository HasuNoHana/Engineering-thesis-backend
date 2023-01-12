package common.commonbackend.users.house_buddy;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.users.User;
import common.commonbackend.users.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<UserDTO> createUserDTOsFromHouseBuddies(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(createUserDTOFromHouseBuddy(user.getHouseBuddy()));
        }
        return userDTOS;
    }
}
