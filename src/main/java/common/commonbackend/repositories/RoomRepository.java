package common.commonbackend.repositories;

import common.commonbackend.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    Room getRoomById(Long id);
}
