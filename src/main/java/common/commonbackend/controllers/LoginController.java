package common.commonbackend.controllers;
import common.commonbackend.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Log4j2
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserSignup userSignup) {
        log.debug("User signup: {}", userSignup);
        String username = userSignup.getUsername();
        String password = userSignup.getPassword();
        String joinCode = userSignup.getHouseJoinCode();
        log.debug("Creating user with username: {} and password: {} and join code: {}", username, password, joinCode);
        userService.createUser(username, password, joinCode);
    }

}