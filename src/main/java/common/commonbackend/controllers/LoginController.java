package common.commonbackend.controllers;

import common.commonbackend.entities.Task;
import common.commonbackend.entities.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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