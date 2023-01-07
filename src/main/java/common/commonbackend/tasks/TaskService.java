package common.commonbackend.tasks;

import common.commonbackend.dto.TaskDTO;
import common.commonbackend.entities.Room;
import common.commonbackend.entities.Task;
import common.commonbackend.house.HouseEntity;
import common.commonbackend.repositories.RoomRepository;
import common.commonbackend.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final RoomRepository roomRepository;
    private final TaskPriceUpdaterService taskPriceUpdaterService;

    public Task getTask(Long id, HouseEntity myHouse) {
        log.debug("Looking for task with id: " + id);
        Task task = taskRepository.getTaskByIdAndRoom_House(id, myHouse);
        log.debug("Found task: " + task);
         return taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task);
    }

    public List<Task> getTasks(HouseEntity myHouse) {
        return taskPriceUpdaterService.getTasksWithUpdatedPrice(taskRepository.findTaskByRoom_House(myHouse));
    }

    public List<Task> getToDoTasks(HouseEntity myHouse) {
        return taskPriceUpdaterService.getTasksWithUpdatedPrice(taskRepository.findTaskByDoneAndRoom_House(false, myHouse));
    }

    public List<Task> getDoneTasks(HouseEntity myHouse) {
        return taskPriceUpdaterService.getTasksWithUpdatedPrice(taskRepository.findTaskByDoneAndRoom_House(true, myHouse));
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.getTaskById(id);
        taskRepository.delete(task);
    }

    public Task saveNewTask(TaskDTO taskDTO) {
        log.debug("Got taskDTO: {}", taskDTO);
        Room room = roomRepository.getRoomById(taskDTO.getRoomId());
        Task task = Task.fromDto(taskDTO, room);  //TODO should room be updated?
        log.debug("Converted to task: {}", task);
        return taskRepository.save(task);
    }

    public Task saveUpdatedTask(Long id, TaskDTO updatedTask) {
        Task task = taskRepository.getTaskById(id);
        Room room = roomRepository.getRoomById(updatedTask.getRoomId());
        task.updateFromDto(updatedTask, room);
        return taskRepository.save(task);
    }

    public Task setTaskDone(Long id, HouseEntity house, boolean done) {
        Task task = getTask(id, house);
        task.setDone(done);
        task =  taskRepository.save(task);
        return task;
    }
}
