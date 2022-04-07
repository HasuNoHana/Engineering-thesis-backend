package common.commonbackend.controllers;

import common.commonbackend.entities.Task;
import common.commonbackend.repositories.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static java.lang.Long.parseLong;

@Log4j2
@Controller
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

    @PostMapping(path = "/task")
    public ResponseEntity<Task> createOrUpdateTask(@RequestParam Map<String, String> params) {
        Task task = new Task(parseLong(params.get("id")), params.get("name"));
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping(path = "/task")
    public ResponseEntity<String> deleteTask(@RequestParam Map<String, String> params) {
        taskRepository.delete(taskRepository.getTaskById(parseLong(params.get("id"))));
        return new ResponseEntity<>(params.get("id"), HttpStatus.OK);
    }

}
