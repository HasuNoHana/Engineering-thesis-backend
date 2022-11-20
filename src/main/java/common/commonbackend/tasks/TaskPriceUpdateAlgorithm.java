package common.commonbackend.tasks;

import java.time.LocalDate;
import java.time.Period;

public interface TaskPriceUpdateAlgorithm {
    int getNewPrice(int price, LocalDate lastDoneDate, Period period);
}
