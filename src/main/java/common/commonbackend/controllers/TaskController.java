package common.commonbackend.controllers;

import common.commonbackend.ControllerHelper;
import common.commonbackend.dto.TaskDTO;
import common.commonbackend.entities.Task;
import common.commonbackend.tasks.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private final ControllerHelper controllerHelper;

    @GetMapping(path = "/task")
    public ResponseEntity<Task> getTaskById(@RequestParam Long id) {
        Task task = taskService.getTask(id,controllerHelper.getMyHouse());
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping(path = "/todo_tasks")
    public ResponseEntity<Iterable<Task>> getToDoTasks() {
        Iterable<Task> todoTasks = taskService.getToDoTasks(controllerHelper.getMyHouse());
        return new ResponseEntity<>(todoTasks, HttpStatus.OK);
    }

    @GetMapping(path = "/done_tasks")
    public ResponseEntity<Iterable<Task>> getDoneTasks() {
        Iterable<Task> todoTasks = taskService.getDoneTasks(controllerHelper.getMyHouse());
        return new ResponseEntity<>(todoTasks, HttpStatus.OK);
    }

    @PostMapping(path = "/task")
    public ResponseEntity<Task> createOrUpdateTask(@RequestBody TaskDTO taskDTO) {
        log.info("createOrUpdateTask");
        Task task = taskService.saveTask(taskDTO);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping(path = "/task")
    public ResponseEntity<Long> deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
