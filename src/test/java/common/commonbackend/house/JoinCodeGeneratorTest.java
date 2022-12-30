package common.commonbackend.house;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MockitoSettings
class JoinCodeGeneratorTest {

    @Test
    void shouldGenerateNewJoinCode() {
        //given
        JoinCodeGenerator joinCodeGenerator = new JoinCodeGenerator();

        //when
        String joinCode = joinCodeGenerator.generateNewJoinCode();

        //then
        assertEquals(4, joinCode.length());
        assertTrue(joinCode.matches("[0-9]+"));
    }

}