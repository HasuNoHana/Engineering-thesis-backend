package common.commonbackend.repositories;

import common.commonbackend.entities.Room;
import common.commonbackend.house.HouseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    Room getRoomById(Long id);

    List<Room> findRoomsByHouse(HouseEntity house);
}
