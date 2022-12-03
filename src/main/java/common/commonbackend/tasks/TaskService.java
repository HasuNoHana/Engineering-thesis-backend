package common.commonbackend.tasks;

import common.commonbackend.dto.TaskDTO;
import common.commonbackend.entities.Task;
import common.commonbackend.repositories.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskPriceUpdaterService taskPriceUpdaterService;

    public TaskService(TaskRepository taskRepository, TaskPriceUpdaterService taskPriceUpdaterService) {
        this.taskRepository = taskRepository;
        this.taskPriceUpdaterService = taskPriceUpdaterService;
    }

    public Task getTask(Long id) {
        log.debug("Looking for task with id: " + id);
        Task task = taskRepository.getTaskById(id);
        log.debug("Found task: " + task);
        return taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task);
    }

    public List<Task> getToDoTasks() {
        return taskPriceUpdaterService.getTasksWithUpdatedPrice(taskRepository.findTaskByDone(false));
    }

    public List<Task> getDoneTasks() {
        return taskPriceUpdaterService.getTasksWithUpdatedPrice(taskRepository.findTaskByDone(true));
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.getTaskById(id);
        taskRepository.delete(task);
    }

    public Task saveTask(TaskDTO taskDTO) {
        log.debug("Got taskDTO: {}", taskDTO);
        Task task = Task.fromDto(taskDTO);  //TODO should room be updated?
        log.debug("Converted to task: {}", task);
        return taskRepository.save(task);
    }
}
