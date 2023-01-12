package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task getTaskById(Long id);

    List<Task> findTaskByDoneAndRoom_House(boolean done, HouseEntity house); //NOSONAR

    List<Task> findTaskByRoom_House(HouseEntity house); //NOSONAR

    Task getTaskByIdAndRoom_House(Long id, HouseEntity myHouse); //NOSONAR
}

