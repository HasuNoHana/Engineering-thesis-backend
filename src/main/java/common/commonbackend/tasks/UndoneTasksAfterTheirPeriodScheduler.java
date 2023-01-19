package common.commonbackend.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class UndoneTasksAfterTheirPeriodScheduler {

    private final TaskService taskService;

    @Scheduled(cron = "50 23 * * * *")
    public void undoneTasksAfterTheirPeriod() {
        log.info("Begin undone tasks after their period");
        taskService.undoneTasksAfterTheirPeriod();
        log.info("Done reset tasks");
    }

}
