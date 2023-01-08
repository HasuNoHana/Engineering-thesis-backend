package common.commonbackend.rooms;

import common.commonbackend.house.HouseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room createRoom(RoomDTO roomDTO, HouseEntity house) {
        Room room = new Room(roomDTO.getName(), roomDTO.getImage(), house);
        return roomRepository.save(room);
    }

    public List<Room> getRoomsForHouse(HouseEntity house) {
        return roomRepository.findRoomsByHouse(house);
    }
}