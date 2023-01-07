package common.commonbackend.tasks.updatealgorithms;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;

@Log4j2
@Component
@RequiredArgsConstructor
class ExponentialTaskPriceUpdateAlgorithm implements TaskPriceUpdateAlgorithm {
    private final Clock clock;

    @Override
    public long getNewPrice(long oldPrice, LocalDate lastDoneDate, Period repetitionRate) {
        long daysFromLastDone = Period.between(lastDoneDate, LocalDate.now(clock)).getDays();

        if (daysFromLastDone < repetitionRate.getDays()) {
            return oldPrice;
        }

        double superscript = 1.0 * daysFromLastDone / repetitionRate.getDays();
        double factor = Math.pow(3.0 / 2, superscript - 1); // TODO taka sama nazwa jak uzywana w reszcze pracy
        return (long) (oldPrice * factor);
    }
}
