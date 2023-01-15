package common.commonbackend.rooms;

import common.commonbackend.houses.HouseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    Room createRoom(RoomDTO roomDTO, HouseEntity house) {
        Room room = new Room(roomDTO.getName(), roomDTO.getImage(), house);
        return roomRepository.save(room);
    }

    List<Room> getRoomsForHouse(HouseEntity house) {
        return roomRepository.findRoomsByHouse(house);
    }

    Room updateRoom(RoomDTO roomDTO, HouseEntity myHouse) {
        Room room = roomRepository.getRoomByIdAndHouse(roomDTO.getId(), myHouse);
        room.updateFromDTO(roomDTO);
        return roomRepository.save(room);
    }

    Room deleteRoom(long id, HouseEntity myHouse) {
        Room room = roomRepository.getRoomByIdAndHouse(id, myHouse);
        roomRepository.delete(room);
        return room;
    }
}
