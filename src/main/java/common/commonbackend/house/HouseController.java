package common.commonbackend.house;


import common.commonbackend.ControllerHelper;
import common.commonbackend.user.User;
import common.commonbackend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HouseController {
    private final UserService userService;

    private final ControllerHelper controllerHelper;
    private final HouseService houseService;

    @PostMapping(path = "/createHouse")
    public ResponseEntity<String> createHouse(@RequestBody long userId) {
        log.info("createHouse for user with id: " + userId);
        User user = userService.getUserById(userId);
        String joinCode = houseService.createHouseForUser(user).getJoinCode();
        return new ResponseEntity<>(joinCode, HttpStatus.OK);
    }

    @GetMapping(path = "/joinCode")
    public ResponseEntity<String> getJoinCode() {
        String joinCode = controllerHelper.getMyHouse().getJoinCode();
        return new ResponseEntity<>(joinCode, HttpStatus.OK);
    }

}
