package common.commonbackend.tasks;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.updatealgorithms.TaskPriceUpdaterService;
import common.commonbackend.users.User;
import common.commonbackend.users.house_buddy.HouseBuddyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final HouseBuddyService houseBuddyService;

    Task getTask(Long id, HouseEntity myHouse) {
        log.debug("Looking for task with id: " + id);
        Task task = Task.fromEntity(taskRepository.getTaskByIdAndRoom_House(id, myHouse));
        log.debug("Found task: " + task);
        return taskPriceUpdaterService.getOneTaskWithUpdatedPrice(task);
    }

    public List<Task> getTasks(HouseEntity myHouse) {
        return taskPriceUpdaterService.getTasksWithUpdatedPrice(
                taskRepository.findTasksByRoom_House(myHouse)
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
            throw new IllegalArgumentException("Room with id: " + taskDTO.getRoom().getId() + " does not exist");
        }
        Task task = Task.fromDto(taskDTO);
        task.setRoom(room);
        log.debug("Converted to task: {}", task);
        return Task.fromEntity(taskRepository.save(task.toEntity()));
    }

    Task saveUpdatedTask(TaskDTO updatedTask, HouseEntity house) {
        TaskEntity originalTask = taskRepository.getTaskById(updatedTask.getId());
        Room room = roomRepository.getRoomByIdAndHouse(updatedTask.getRoom().getId(), house);
        Task newTask = new TaskBuilder()
                .setId(updatedTask.getId())
                .setName(updatedTask.getName())
                .setInitialPrice(updatedTask.getInitialPrice())
                .setDone(originalTask.isDone())
                .setRoom(room)
                .setLastDoneDate(updatedTask.getLastDoneDate())
                .setPreviousLastDoneDate(originalTask.getPreviousLastDoneDate())
                .setPreviousLastDoneUserId(originalTask.getPreviousLastDoneUserId())
                .setRepetitionRate(Period.ofDays(updatedTask.getRepetitionRateInDays()))
                .setBeginPeriodDate(originalTask.getBeginPeriodDate())
                .setLastDonePrice(originalTask.getLastDonePrice())
                .createTask();
        return Task.fromEntity(taskRepository.save(newTask.toEntity()));
    }

    Task setTaskDone(Long id, HouseEntity house, boolean done, User user) {
        Task task = getTask(id, house);
        if (done) {
            task.setPreviousLastDoneDate(task.getLastDoneDate());
            task.setPreviousLastDoneUserId(task.getLastDoneUserId());
            task.setLastDoneDate(LocalDate.now());
            task.setLastDoneUserId(user.getId());
            task.setLastDonePrice(task.getCurrentPrice().isPresent() ? task.getCurrentPrice().get() : 0);//NOSONAR
            houseBuddyService.addPointsToUser(user, task.getCurrentPrice());
        } else {
            if (task.getLastDoneUserId() != user.getId()) {
                return task;
            }
            task.setLastDoneDate(task.getPreviousLastDoneDate());
            task.setLastDoneUserId(task.getPreviousLastDoneUserId());
            houseBuddyService.substractPointsFromUser(user, task.getLastDonePrice());
        }
        task.setDone(done);
        return Task.fromEntity(taskRepository.save(task.toEntity()));
    }

    public int getNumberOfNotDoneTasks(Room room) {
        return this.taskRepository.countTasksByRoomAndDone(room, false);
    }
}
