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
    public ResponseEntity<Iterable<RoomDTO>> getRooms() {
        Iterable<RoomDTO> rooms = roomService.getRoomsForHouse(controllerHelper.getMyHouse());
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping(path = "/addRoom")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO room = roomService.createRoom(roomDTO, controllerHelper.getMyHouse());
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping(path = "/updateRoom")
    public ResponseEntity<RoomDTO> updateRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO room = roomService.updateRoom(roomDTO, controllerHelper.getMyHouse());//TODO wywal ten id z wolania metody
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteRoom")
    public ResponseEntity<Long> deleteRoom(@RequestParam long id) {
        roomService.deleteRoom(id, controllerHelper.getMyHouse());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
