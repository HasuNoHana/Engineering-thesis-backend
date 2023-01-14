package common.commonbackend.tasks.updatealgorithms;

import common.commonbackend.tasks.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskPriceUpdaterService {
    private final TaskPriceUpdateAlgorithm taskPriceUpdateAlgorithm;

    public long getUpdatedPrice(long initialPrice, LocalDate lastDoneDate, Period repetitionRate) {
        return taskPriceUpdateAlgorithm.getNewPrice(
                initialPrice,
                lastDoneDate,
                repetitionRate);
    }

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
