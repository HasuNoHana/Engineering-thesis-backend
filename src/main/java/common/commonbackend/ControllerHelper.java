package common.commonbackend;

import common.commonbackend.house.HouseEntity;
import common.commonbackend.security.UserPrincipal;
import common.commonbackend.user.User;
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
        HouseEntity house = currentUser.getHouse();
        log.debug("Current user: {}, house {}", currentUser.getUsername(), house);
        return house;
    }
}
