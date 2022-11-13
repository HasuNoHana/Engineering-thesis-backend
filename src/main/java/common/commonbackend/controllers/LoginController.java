package common.commonbackend.controllers;
import common.commonbackend.entities.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Log4j2
@RestController
@RequestMapping("/api")
public class LoginController {

    @GetMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/register")
    @ResponseBody
    public User user(User user) {
        return user;
    }

}