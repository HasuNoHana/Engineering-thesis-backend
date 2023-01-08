package common.commonbackend.rooms;

import common.commonbackend.ControllerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomController {
    private final ControllerHelper controllerHelper;
    private final RoomService roomService;

    @GetMapping(path = "/rooms")
    public ResponseEntity<Iterable<Room>> getRooms() {
        Iterable<Room> rooms = roomService.getRoomsForHouse(controllerHelper.getMyHouse());
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping(path = "/addRoom")
    public ResponseEntity<Room> createRoom(@RequestBody RoomDTO roomDTO) {
        Room room = roomService.createRoom(roomDTO, controllerHelper.getMyHouse());
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping(path = "/updateRoom")
    public ResponseEntity<Room> updateRoom(@RequestParam long id, @RequestBody RoomDTO roomDTO) {
        Room room = roomService.updateRoom(id, roomDTO, controllerHelper.getMyHouse());
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
