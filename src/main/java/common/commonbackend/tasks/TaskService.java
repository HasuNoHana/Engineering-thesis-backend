package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.updatealgorithms.TaskPriceUpdaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final RoomRepository roomRepository;
    private final TaskPriceUpdaterService taskPriceUpdaterService;

    Task getTask(Long id, HouseEntity myHouse) {
        log.debug("Looking for task with id: " + id);
        Task task = Task.fromEntity(taskRepository.getTaskByIdAndRoom_House(id, myHouse));
        log.debug("Found task: " + task);
        return taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task);
    }

    List<Task> getTasks(HouseEntity myHouse) {
        return taskPriceUpdaterService.getTasksWithUpdatedPrice(
                taskRepository.findTaskByRoom_House(myHouse)
                        .stream()
                        .map(Task::fromEntity)
                        .map(taskPriceUpdaterService::getOneTaskWithUpdatedPrice)
                        .collect(Collectors.toList()));
    }


    void deleteTask(Long id) {
        Task task = Task.fromEntity(taskRepository.getTaskById(id));
        taskRepository.delete(task.toEntity());
    }

    Task saveNewTask(TaskDTO taskDTO, HouseEntity myHouse) {
        log.debug("Got taskDTO: {}", taskDTO);
        Room room = roomRepository.getRoomByIdAndHouse(taskDTO.getRoom().getId(), myHouse);
        if (room == null) {
            log.error("Room with id: " + taskDTO.getRoom().getId() + " not found");
            return null;
        }
        Task task = Task.fromDto(taskDTO);
        task.setRoom(room);
        log.debug("Converted to task: {}", task);
        return Task.fromEntity(taskRepository.save(task.toEntity()));
    }

    Task saveUpdatedTask(TaskDTO updatedTask, HouseEntity house) {
        TaskEntity originalTask = taskRepository.getTaskById(updatedTask.getId());
        Room room = roomRepository.getRoomByIdAndHouse(updatedTask.getRoom().getId(), house);
        Task newTask = new Task(
                updatedTask.getId(),
                updatedTask.getName(),
                updatedTask.getInitialPrice(),
                originalTask.isDone(),
                room,
                updatedTask.getLastDoneDate(),
                Period.ofDays(updatedTask.getRepetitionRateInDays()));
        return Task.fromEntity(taskRepository.save(newTask.toEntity()));
    }

    Task setTaskDone(Long id, HouseEntity house, boolean done) {
        Task task = getTask(id, house);
        task.setDone(done);
        return Task.fromEntity(taskRepository.save(task.toEntity()));
    }
}
