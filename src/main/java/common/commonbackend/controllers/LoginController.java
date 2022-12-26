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

    @PostMapping("/createUser")
    public void createUser(@RequestBody String username, @RequestBody String password) {
        log.info("Creating user with username: " + username + " and password: " + password);
        userService.createUser(username, password);
    }

}