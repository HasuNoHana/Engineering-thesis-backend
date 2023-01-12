package common.commonbackend;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.security.UserPrincipal;
import common.commonbackend.users.User;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@NoArgsConstructor
public class ControllerHelper {
    public HouseEntity getMyHouse() {
        final User currentUser = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        HouseEntity house = currentUser.getHouseBuddy().getHouse();
        log.debug("Current user: {}, house {}", currentUser.getUsername(), house);
        return house;
    }

    public User getMyUser() { // TODO czy to jest potrzebbe?
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }
}
