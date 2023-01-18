package common.commonbackend.users.authorization;

import common.commonbackend.ControllerHelper;
import common.commonbackend.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ModifyUserController {

    private final ControllerHelper controllerHelper;

    private final UserService userService;

    @PostMapping(path = "/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody Passwords passwords) {
        String currentPassword = passwords.getCurrentPassword();
        String newPassword = passwords.getNewPassword();
        userService.changePassword(currentPassword, newPassword, controllerHelper.getMyUser());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteCurrentUser")
    public ResponseEntity<Boolean> deleteCurrentUser() {
        userService.deleteUser(controllerHelper.getMyUser());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Value
    protected static class Passwords {
        String currentPassword;
        String newPassword;
    }
}
