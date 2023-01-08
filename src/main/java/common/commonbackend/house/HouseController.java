package common.commonbackend.house;


import common.commonbackend.user.User;
import common.commonbackend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HouseController {
    private final UserService userService;

    private final HouseService houseService;

    @PostMapping(path = "/createHouse")
    public ResponseEntity<String> createHouse(@RequestBody long userId) {
        log.info("createHouse for user with id: " + userId);
        User user = userService.getUserById(userId);
        String joinCode = houseService.createHouseForUser(user).getJoinCode();
        return new ResponseEntity<>(joinCode, HttpStatus.OK);
    }

}
