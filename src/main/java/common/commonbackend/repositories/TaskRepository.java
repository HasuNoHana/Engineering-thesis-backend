package common.commonbackend.repositories;

import common.commonbackend.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task getTaskById(Long id);

    default Iterable<Task> findByDone(boolean done) {
        //TODO change dummy implementation when initializing database //NOSONAR
        if (done) {
            return List.of(new Task("task3", 30, true));
        } else {
            return List.of(new Task("task1", 10, false),
                    new Task("task2", 20, false));
        }
    }

    @Override
    default Iterable<Task> findAll() {
        //TODO change dummy implementation when initializing database //NOSONAR
        return List.of(new Task("task1", 10, false),
                new Task("task2", 20, false),
                new Task("task3", 30, true));
    }
}

