package common.commonbackend.tasks.updatealgorithms;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExponentialTaskPriceUpdateAlgorithmTest {


    public static final LocalDate DATE = LocalDate.of(2022, 1, 1);
    public static final Period DAY_PERIOD = Period.ofDays(1);
    private static final Period WEEK_PERIOD = Period.ofWeeks(1);
    @Mock
    private Clock clock;

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void shouldCorrectlyCalculateExponentialIncrement(int price, LocalDate lastDoneDate, Period period, LocalDate dateNow, int expectedPrice) {
        //given
        when(clock.instant()).thenReturn(dateNow.atStartOfDay().toInstant(ZoneOffset.UTC));
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        ExponentialTaskPriceUpdateAlgorithm exponentialTaskPriceUpdateAlgorithm = new ExponentialTaskPriceUpdateAlgorithm(clock);

        //when
        long actualPrice = exponentialTaskPriceUpdateAlgorithm.getNewPrice(price, lastDoneDate, period);

        //then
        assertThat(actualPrice).isEqualTo(expectedPrice);
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(10, DATE, WEEK_PERIOD, DATE.plusDays(0), 10),
                Arguments.of(10, DATE, WEEK_PERIOD, DATE.plusDays(WEEK_PERIOD.getDays() / 2), 10),
                Arguments.of(10, DATE, DAY_PERIOD, DATE.plusDays(DAY_PERIOD.getDays()), 10),
                Arguments.of(10, DATE, DAY_PERIOD, DATE.plusDays(DAY_PERIOD.getDays() * 2), 15),
                Arguments.of(10, DATE, DAY_PERIOD, DATE.plusDays(DAY_PERIOD.getDays() * 3), 22), // 22.5 but cast to int
                Arguments.of(10, DATE, WEEK_PERIOD, DATE.plusDays(10), 11), // 11.89 but cast to int
                Arguments.of(8, DATE, DAY_PERIOD, DATE.plusDays(DAY_PERIOD.getDays() * 2), 12)

                );
    }
}