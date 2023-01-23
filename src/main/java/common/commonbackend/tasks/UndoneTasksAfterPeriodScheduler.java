package common.commonbackend.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
@RequiredArgsConstructor
public class UndoneTasksAfterPeriodScheduler {
    private final TaskRepository taskRepository;

    @Scheduled(cron = "0 50 23 * * *")
    public void undoneTasksAfterTheirPeriod() {
        log.info("Begin undone tasks after their period");

        Iterable<TaskEntity> allTasks = taskRepository.findAll();
        List<TaskEntity> collect = StreamSupport.stream(allTasks.spliterator(), false)
                .map(Task::fromEntity)
                .filter(this::shouldBeReset)
                .peek(t -> log.debug("Following task will be reset {}", t.logForScheduler())) // NOSONAR only for logging purposes
                .map(Task::reset)
                .map(Task::toEntity)
                .collect(Collectors.toList());
        log.info("reset {} tasks", collect.size());
        taskRepository.saveAll(collect);

        log.info("Done reset tasks");
    }

    private boolean shouldBeReset(Task task) {
        log.debug("Checking if task should be reseted {}", task.logForScheduler());
        return task.isDone() && task.getLastDoneDate().isBefore(
                LocalDate.now().minus(
                        task.getRepetitionRate().minusDays(1))); // minus 1 because also equal to beginPeriodDate is counted
    }

}
