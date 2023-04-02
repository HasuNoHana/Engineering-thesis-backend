package common.commonbackend.dev;

import common.commonbackend.tasks.UndoneTasksAfterPeriodScheduler;
import common.commonbackend.users.TaxCollectorScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HelloWorldController {

    @GetMapping(path = "/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello smieciu!", HttpStatus.OK);
    }
}
