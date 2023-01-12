package common.commonbackend.users.login;

import common.commonbackend.houses.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    @PostMapping("/createUserAndHouse")
    public ResponseEntity<String> createUserAndHouse(@RequestBody UserWithoutHouse userWithoutHouse) {
        log.debug("User signup: {}", userWithoutHouse);
        String username = userWithoutHouse.getUsername();
        String password = userWithoutHouse.getPassword();
        log.debug("Creating user with username: {} and password: {}", username, password);

        try {
            userService.createUser(username, password);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/createUserForExistingHouse")
    public ResponseEntity<String> createUserForExistingHouse(@RequestBody UserAndJoinCode userAndJoinCode) {  // TODO what should be returned?
        String username = userAndJoinCode.getUsername();
        String password = userAndJoinCode.getPassword();
        String joinCode = userAndJoinCode.getHouseJoinCode();
        log.info("addUserToHouse for user with username: " + username);
        log.debug("joinCode: " + joinCode);

        try {
            userService.createUser(username, password, joinCode);
        } catch (WrongHouseJoinCodeException e) {
            log.error("Wrong house join code: " + joinCode);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Value
    public static class UserWithoutHouse {
        String username;
        String password;
    }

    @Value
    public static class UserAndJoinCode {
        String username;
        String password;
        String houseJoinCode;
    }

}
