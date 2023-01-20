package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

    TaskEntity getTaskById(Long id);

    List<TaskEntity> findTasksByRoom_House(HouseEntity house); //NOSONAR

    TaskEntity getTaskByIdAndRoom_House(Long id, HouseEntity myHouse); //NOSONAR

    int countTasksByRoomAndDone(Room room, boolean done);
}

