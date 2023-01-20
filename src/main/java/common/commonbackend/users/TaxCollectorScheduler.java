package common.commonbackend.users;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaxCollectorScheduler {

    private final UserService userService;

    @Scheduled(cron = "0 50 23 * * *")
    public void substractContributionFromUsers() {
        log.info("Beginning substructing contribution from users");
        userService.substractContribiutionFromAllUsers();
        log.info("Done substructing contribution from users");
    }

}
