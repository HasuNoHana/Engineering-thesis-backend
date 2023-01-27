package common.commonbackend.users;

import common.commonbackend.ControllerHelper;
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
    public ResponseEntity<UserDTO> getCurrentUserData() {
        User user = controllerHelper.getMyUser();
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getHouseBuddy().getCurrentPoints(),
                user.getHouseBuddy().getWeeklyContribution(), user.getHouseBuddy().getAvatarImageUrl());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOs = houseBuddyService
                .createUserDTOsFromHouseBuddies(userService.getUsersForHouse(controllerHelper.getMyHouse()));
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PostMapping(path = "/editUser")
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO newUser) {
        User user;
        if (newUser.getId() == -1) {
            user = userService.editUser(controllerHelper.getMyUser().getId(), newUser);

        } else {
            user = userService.editUser(newUser.getId(), newUser);

        }
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getHouseBuddy().getCurrentPoints(),
                user.getHouseBuddy().getWeeklyContribution(), user.getHouseBuddy().getAvatarImageUrl());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/doneTasksThisWeek")
    public ResponseEntity<Long> getDoneTasksThisBeginPeriod() {
        long doneTasksThisWeek = userService
                .countDoneTasksThisWeek(controllerHelper.getMyUser().getId(), controllerHelper.getMyHouse());
        return new ResponseEntity<>(doneTasksThisWeek, HttpStatus.OK);
    }

}