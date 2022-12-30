package common.commonbackend.house;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class JoinCodeGenerator {
    private final Random random = new Random();

    public String generateNewJoinCode() {
        int generatedCode = this.random.nextInt(9000) + 1000; //range 1000-9999
        return Integer.toString(generatedCode);
    }
}
