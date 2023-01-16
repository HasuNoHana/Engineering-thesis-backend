package common.commonbackend.tasks;

import common.commonbackend.ControllerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private final ControllerHelper controllerHelper;

    @PostMapping(path = "/makeTaskDone")
    public ResponseEntity<TaskDTO> makeTaskDone(@RequestParam Long id) {
        Task task = taskService.setTaskDone(id, controllerHelper.getMyHouse(), true,
                controllerHelper.getMyUser().getId());
        return new ResponseEntity<>(task.toDto(), HttpStatus.OK);
    }

    @PostMapping(path = "/makeTaskToDo")
    public ResponseEntity<TaskDTO> makeTaskToDo(@RequestParam Long id) {
        Task task = taskService.setTaskDone(id, controllerHelper.getMyHouse(), false,
                controllerHelper.getMyUser().getId());
        return new ResponseEntity<>(task.toDto(), HttpStatus.OK);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<Iterable<TaskDTO>> getTasks() {
        List<Task> tasks = taskService.getTasks(controllerHelper.getMyHouse());
        return new ResponseEntity<>(toDto(tasks), HttpStatus.OK);
    }

    protected static List<TaskDTO> toDto(List<Task> tasks) {
        return tasks.stream().map(Task::toDto).collect(Collectors.toList());
    }

    @PostMapping(path = "/updateTask")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO) {
        log.debug("Update task with id: " + taskDTO.getId());
        Task task = taskService.saveUpdatedTask(taskDTO, controllerHelper.getMyHouse());
        return new ResponseEntity<>(task.toDto(), HttpStatus.OK);
    }

    @PostMapping(path = "/addTask")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskService.saveNewTask(taskDTO, controllerHelper.getMyHouse());
        return new ResponseEntity<>(task.toDto(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/task")
    public ResponseEntity<Long> deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
