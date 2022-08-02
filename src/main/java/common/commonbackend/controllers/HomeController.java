package common.commonbackend.controllers;

import common.commonbackend.entities.Task;
import common.commonbackend.repositories.RoomRepository;
import common.commonbackend.repositories.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.lang.Long.parseLong;

@Log4j2
@RestController
@RequestMapping("/api")
public class HomeController {

    private final TaskRepository taskRepository;
    private final RoomRepository roomRepository;

    public HomeController(TaskRepository taskRepository, RoomRepository roomRepository) {
        this.taskRepository = taskRepository;
        this.roomRepository = roomRepository;
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
    public ResponseEntity<Task> createOrUpdateTask(@RequestParam Map<String, String> params) {
        Task task = new Task(
                parseLong(params.get("id")),
                params.get("name"),
                Integer.parseInt(params.get("price")),
                Boolean.parseBoolean(params.get("done")),
                roomRepository.getRoomById(Long.parseLong(params.get("roomId")))
        );
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping(path = "/task")
    public ResponseEntity<Long> deleteTask(@RequestParam Long id) {
        taskRepository.delete(taskRepository.getTaskById(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
