package common.commonbackend.users;

import common.commonbackend.ControllerHelper;
import common.commonbackend.users.house_buddy.HouseBuddy;
import common.commonbackend.users.house_buddy.HouseBuddyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final ControllerHelper controllerHelper;

    private final UserService userService;

    private final HouseBuddyService houseBuddyService;

    @GetMapping(path = "/currentUserData")
    public ResponseEntity<HouseBuddy> getCurrentUserData() {
        HouseBuddy houseBuddy = houseBuddyService
                .getHouseBuddyById(controllerHelper.getMyUser().getHouseBuddy().getId());
        return new ResponseEntity<>(houseBuddy, HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOs = houseBuddyService
                .createUserDTOsFromHouseBuddies(userService.getUsersForHouse(controllerHelper.getMyHouse()));
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PostMapping(path = "/editUser")
    public ResponseEntity<HouseBuddy> editUser(@RequestParam Long id, @RequestBody UserDTO newUser) {
        if (id == -1) {
            id = controllerHelper.getMyUser().getId();
        }
        User user = userService.editUser(id, newUser);
        return new ResponseEntity<>(user.getHouseBuddy(), HttpStatus.OK);
    }

    @GetMapping(path = "/doneTasksThisWeek")
    public ResponseEntity<Long> getDoneTasksThisWeek() {
        long doneTasksThisWeek = userService
                .countDoneTasksThisWeek(controllerHelper.getMyUser().getId(), controllerHelper.getMyHouse());
        return new ResponseEntity<>(doneTasksThisWeek, HttpStatus.OK);
    }

}