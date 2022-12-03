package common.commonbackend.repositories;

import common.commonbackend.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task getTaskById(Long id);

    List<Task> findTaskByDone(boolean done);
}

