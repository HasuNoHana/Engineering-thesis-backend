package common.commonbackend.controllers;

import common.commonbackend.entities.Room;
import common.commonbackend.repositories.RoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api")
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping(path = "/rooms")
    public ResponseEntity<Iterable<Room>> getRooms() {
        Iterable<Room> rooms = this.roomRepository.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @DeleteMapping(path = "/room/delete")
    public ResponseEntity<Long> deleteRoom(@RequestParam Long id) {
        roomRepository.delete(roomRepository.getRoomById(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
