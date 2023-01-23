package common.commonbackend.tasks.updatealgorithms;

import java.time.LocalDate;
import java.time.Period;

interface TaskPriceUpdateAlgorithm {
    long getNewPrice(long oldPrice, LocalDate beginPeriodDate, Period repetitionRate);
}
