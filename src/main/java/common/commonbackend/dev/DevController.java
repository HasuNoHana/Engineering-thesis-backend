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
@RequestMapping("/api/dev")
@RequiredArgsConstructor
public class DevController {
    private final UndoneTasksAfterPeriodScheduler undoneTasksAfterPeriodScheduler;
    private final TaxCollectorScheduler taxCollectorScheduler;

    @GetMapping(path = "/undoneTask")
    public ResponseEntity<String> undoneTask() {
        undoneTasksAfterPeriodScheduler.undoneTasksAfterTheirPeriod();
        return new ResponseEntity<>("done!", HttpStatus.OK);
    }

    @GetMapping(path = "/getTaxes")
    public ResponseEntity<String> getTaxes() {
        log.info("It's time to collect some taxes!");
        taxCollectorScheduler.substractContributionFromUsers();
        return new ResponseEntity<>("You poor bastards!!", HttpStatus.OK);
    }
}
