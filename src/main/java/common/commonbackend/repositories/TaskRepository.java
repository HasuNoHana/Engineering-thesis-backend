package common.commonbackend.repositories;

import common.commonbackend.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task getTaskById(Long id);

    @Override
    default Iterable<Task> findAll() {
        return List.of(new Task("task1"),
                new Task("task2"),
                new Task("task3"));
    }
}

