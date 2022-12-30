package common.commonbackend.house;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class JoinCodeGenerator {

    private final Random random;

    public JoinCodeGenerator(Random random) {
        this.random = random;
    }

    public String generateNewJoinCode() {
        int generatedCode = this.random.nextInt(9000) + 1000; //range 1000-9999
        return Integer.toString(generatedCode);
    }
}
