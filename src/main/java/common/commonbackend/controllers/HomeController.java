package common.commonbackend.controllers;

import common.commonbackend.entities.Task;
import common.commonbackend.repositories.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class HomeController {

    private final TaskRepository taskRepository;

    public HomeController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @RequestMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("home", HttpStatus.OK);
    }

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public ResponseEntity<Task> getTaskById(@RequestParam Long id) {
        Task task = taskRepository.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

}
