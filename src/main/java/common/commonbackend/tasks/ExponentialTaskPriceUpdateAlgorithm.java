package common.commonbackend.tasks;

import lombok.extern.log4j.Log4j2;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
@Log4j2
public class ExponentialTaskPriceUpdateAlgorithm implements TaskPriceUpdateAlgorithm {

    private final Clock clock;

    public ExponentialTaskPriceUpdateAlgorithm(Clock clock) {
        this.clock = clock;
    }

    @Override
    public long getNewPrice(long price, LocalDate lastDoneDate, Period period) {
        long daysFromLastDone = Period.between(lastDoneDate, LocalDate.now(clock)).getDays();

        if (daysFromLastDone< period.getDays()) {
            return price;
        }

        double superscript = 1.0 * daysFromLastDone / period.getDays();
        double factor = Math.pow(3.0 / 2, superscript - 1); // TODO taka sama nazwa jak uzywana w reszcze pracy
        log.debug("daysFromLastDone: {}", daysFromLastDone);
        log.debug("superscript: {}", superscript);
        log.debug("factor: {}", factor);
        return (long) (price * factor);
    }
}
