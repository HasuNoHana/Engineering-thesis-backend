package common.commonbackend.house;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@MockitoSettings
class JoinCodeGeneratorTest {

    @Mock
    private Random random;

    @Test
    void shouldGenerateNewJoinCode() {
        //given
        JoinCodeGenerator joinCodeGenerator = new JoinCodeGenerator(random);
        when(random.nextInt(9000)).thenReturn(1000);

        //when
        String joinCode = joinCodeGenerator.generateNewJoinCode();

        //then
        assertTrue(joinCode.matches("\\d{4}"));
    }

}