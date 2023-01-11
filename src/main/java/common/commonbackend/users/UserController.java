package common.commonbackend.users;

import common.commonbackend.ControllerHelper;
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

    @GetMapping(path = "/userData")
    public ResponseEntity<UserDTO> getUserData() {
        User user = controllerHelper.getMyUser();
//        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPoints(), user.getRange());
//        UserDTO userDTO = UserDTO.DTOFromUser(user);
        UserDTO userDTO = new UserDTO(1L, "zuza", 40, 100,
                "https://upload.wikimedia.org/wikipedia/commons/6/6f/Lotus_flower_animation.svg");
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
//        List<User> users = userService.getUsersForHouse(controllerHelper.getMyHouse());
        List<UserDTO> users = List.of(new UserDTO(1L, "zuza", 40, 100, "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg"),
                new UserDTO(2L, "filip", 50, 60, "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg"));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(path = "/edituser")
    public ResponseEntity<UserDTO> editUser(@RequestParam Long id, @RequestBody UserDTO newUser) {
        UserDTO userDTO = new UserDTO(1L, "zuza", 40, 100, "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg");
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/proposedImages")
    public ResponseEntity<Iterable<String>> getImages() {
        List<String> images = List.of("https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg",
                "https://upload.wikimedia.org/wikipedia/commons/3/35/Red-simple-heart-symbol-only.png",
                "https://upload.wikimedia.org/wikipedia/commons/2/2b/Black_Cat_Vector.svg",
                "https://upload.wikimedia.org/wikipedia/commons/4/47/Anchor_pictogram_grey.svg",
                "https://upload.wikimedia.org/wikipedia/commons/a/a7/Coffe_maker.svg",
                "https://upload.wikimedia.org/wikipedia/commons/0/03/Oxygen480-apps-preferences-web-browser-cookies.svg",
                "https://upload.wikimedia.org/wikipedia/commons/0/0e/Wikimania2019_horse_icon.svg",
                "https://upload.wikimedia.org/wikipedia/commons/0/06/Toicon-icon-hatch-cool.svg",
                "https://upload.wikimedia.org/wikipedia/commons/6/6f/Lotus_flower_animation.svg",
                "https://upload.wikimedia.org/wikipedia/commons/f/f5/Circle-icons-flower.svg",
                "https://upload.wikimedia.org/wikipedia/commons/3/3c/Dog_%28134522%29_-_The_Noun_Project.svg");
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}