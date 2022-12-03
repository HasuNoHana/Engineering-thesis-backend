package common.commonbackend.controllers;

import common.commonbackend.dto.TaskDTO;
import common.commonbackend.entities.Task;
import common.commonbackend.repositories.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api")
public class HomeController {

    private final TaskRepository taskRepository;

    public HomeController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("home", HttpStatus.OK);
    }

    @GetMapping(path = "/task")
    public ResponseEntity<Task> getTaskById(@RequestParam Long id) {
        Task task = taskRepository.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping(path = "/todo_tasks")
    public ResponseEntity<Iterable<Task>> getToDoTasks() {
        Iterable<Task> todoTasks = taskRepository.findTaskByDone(false);
        return new ResponseEntity<>(todoTasks, HttpStatus.OK);
    }

    @GetMapping(path = "/done_tasks")
    public ResponseEntity<Iterable<Task>> getDoneTasks() {
        Iterable<Task> todoTasks = taskRepository.findTaskByDone(true);
        return new ResponseEntity<>(todoTasks, HttpStatus.OK);
    }

    @PostMapping(path = "/task")
    public ResponseEntity<Task> createOrUpdateTask(@RequestBody TaskDTO taskDTO) {
        log.info("createOrUpdateTask");
        log.debug("Got taskDTO: {}", taskDTO);
        Task task = Task.fromDto(taskDTO);  //TODO should room be updated?
        log.debug("Converted to task: {}", task);
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping(path = "/task")
    public ResponseEntity<Long> deleteTask(@RequestParam Long id) {
        taskRepository.delete(taskRepository.getTaskById(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
