package common.commonbackend.tasks;

import common.commonbackend.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskPriceUpdaterService {

    final TaskPriceUpdateAlgorithm taskPriceUpdateAlgorithm;

    public TaskPriceUpdaterService(TaskPriceUpdateAlgorithm taskPriceUpdateAlgorithm) {
        this.taskPriceUpdateAlgorithm = taskPriceUpdateAlgorithm;
    }

    public Task getOneTaskWithUpdatedPrice(Task task) {
        long newPrice = taskPriceUpdateAlgorithm.getNewPrice(task.getInitialPrice(), task.getLastDoneDate(), task.getPeriod());
        return task.getNewTaskWithUpdatedPrice(newPrice);
    }

    public List<Task> getTasksWithUpdatedPrice(List<Task> taskByDone) { // TODO name can be better
        return taskByDone.stream().map(this::getOneTaskWithUpdatedPrice).collect(Collectors.toList());
    }
}
