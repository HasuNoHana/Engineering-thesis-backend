package common.commonbackend.tasks.updatealgorithms;

import common.commonbackend.tasks.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskPriceUpdaterService {
    private final TaskPriceUpdateAlgorithm taskPriceUpdateAlgorithm;

    public Task getOneTaskWithUpdatedPrice(Task task) {
        long newPrice = taskPriceUpdateAlgorithm.getNewPrice(
                task.getInitialPrice(),
                task.getLastDoneDate(),
                task.getRepetitionRate());
        return task.getNewTaskWithUpdatedPrice(newPrice);
    }

    public List<Task> getTasksWithUpdatedPrice(List<Task> tasks) {
        return tasks.stream().map(this::getOneTaskWithUpdatedPrice).collect(Collectors.toList());
    }
}
