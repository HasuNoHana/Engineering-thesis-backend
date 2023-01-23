package common.commonbackend.rooms;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.tasks.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    private final TaskService taskService;

    RoomDTO createRoom(RoomDTO roomDTO, HouseEntity house) {
        Room room = Room.fromDto(roomDTO);
        room.setHouse(house);
        int notDoneTasks = this.taskService.getNumberOfNotDoneTasks(room);
        return roomRepository.save(room).toDto(notDoneTasks);
    }

    List<RoomDTO> getRoomsForHouse(HouseEntity house) {
        return roomRepository.findRoomsByHouse(house)
                .stream()
                .map(room -> room.toDto(this.taskService.getNumberOfNotDoneTasks(room)))
                .collect(Collectors.toList());
    }

    RoomDTO updateRoom(RoomDTO roomDTO, HouseEntity myHouse) {
        Room room = roomRepository.getRoomByIdAndHouse(roomDTO.getId(), myHouse);
        room.updateFromDTO(roomDTO);
        int notDoneTasks = this.taskService.getNumberOfNotDoneTasks(room);
        Room saved = roomRepository.save(room);
        return saved.toDto(notDoneTasks);
    }

    Room deleteRoom(long id, HouseEntity myHouse) {
        Room room = roomRepository.getRoomByIdAndHouse(id, myHouse);
        roomRepository.delete(room);
        return room;
    }
}
