package common.commonbackend.rooms;

import common.commonbackend.ControllerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomController {
    private final RoomRepository roomRepository;
    private final ControllerHelper controllerHelper;

    @GetMapping(path = "/rooms")
    public ResponseEntity<Iterable<Room>> getRooms() {
        Iterable<Room> rooms = this.roomRepository.findRoomsByHouse(controllerHelper.getMyHouse());
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
