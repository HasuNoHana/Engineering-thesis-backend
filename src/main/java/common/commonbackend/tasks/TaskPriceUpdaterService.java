package common.commonbackend.tasks;

import common.commonbackend.entities.Task;

public class TaskPriceUpdaterService {

    final TaskPriceUpdateAlgorithm taskPriceUpdateAlgorithm;

    public TaskPriceUpdaterService(TaskPriceUpdateAlgorithm taskPriceUpdateAlgorithm) {
        this.taskPriceUpdateAlgorithm = taskPriceUpdateAlgorithm;
    }

    public Task updateTask(Task task) {
        long newPrice = taskPriceUpdateAlgorithm.getNewPrice(task.getPrice(), task.getLastDoneDate(), task.getPeriod());
        return task.getNewTaskWithUpdatedPrice(newPrice);
    }
}
