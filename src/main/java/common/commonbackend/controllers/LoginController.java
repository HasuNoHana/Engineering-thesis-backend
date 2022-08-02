package common.commonbackend.controllers;
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

}