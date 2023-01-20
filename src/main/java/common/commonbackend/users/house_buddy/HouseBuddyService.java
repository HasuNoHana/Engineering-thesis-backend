package common.commonbackend.users.house_buddy;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.users.User;
import common.commonbackend.users.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class HouseBuddyService {

    private static final long DEFAULT_FIREWOOD_STACK_SIZE = 0L;
    private static final long DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION = 100L;
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg";
    private final HouseBuddyRepository houseBuddyRepository;

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
                houseBuddy.getCurrentPoints(),
                houseBuddy.getWeeklyContribution(),
                houseBuddy.getAvatarImageUrl());
    }

    public List<UserDTO> createUserDTOsFromHouseBuddies(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(createUserDTOFromHouseBuddy(user.getHouseBuddy()));
        }
        return userDTOS;
    }

    public HouseBuddy getHouseBuddyById(Long id) {
        return houseBuddyRepository.getHouseBuddyById(id);
    }

    public User addPointsToUser(User user, Optional<Long> currentPrice) {
        if (currentPrice.isEmpty()) {
            log.error("Current price is not present");
        } else {
            HouseBuddy houseBuddy = houseBuddyRepository.getHouseBuddyById(user.getHouseBuddy().getId());
            houseBuddy.setCurrentPoints(houseBuddy.getCurrentPoints() + currentPrice.get());
            return houseBuddyRepository.save(houseBuddy).getUser();
        }
        return null;
    }

    public User substractPointsFromUser(User user, Long currentPrice) {
        HouseBuddy houseBuddy = houseBuddyRepository.getHouseBuddyById(user.getHouseBuddy().getId());
        houseBuddy.setCurrentPoints(houseBuddy.getCurrentPoints() - currentPrice);
        return houseBuddyRepository.save(houseBuddy).getUser();
    }
}
