package common.commonbackend.tasks;

import java.time.LocalDate;
import java.time.Period;

public interface TaskPriceUpdateAlgorithm {
    long getNewPrice(long price, LocalDate lastDoneDate, Period period);
}
