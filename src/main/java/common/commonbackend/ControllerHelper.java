package common.commonbackend;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.security.UserPrincipal;
import common.commonbackend.users.User;
import common.commonbackend.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ControllerHelper {
    private final UserService userService;

    public HouseEntity getMyHouse() {
        User currentUser = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser();
        User actualUser = userService.getUserById(currentUser.getId());
        HouseEntity house = actualUser.getHouseBuddy().getHouse();
        log.debug("Current user: {}, house {}", actualUser.getUsername(), house);
        return house;
    }

    public User getMyUser() {
        User currentUser = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser();
        return userService.getUserById(currentUser.getId());
    }
}
