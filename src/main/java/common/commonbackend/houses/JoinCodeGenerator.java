package common.commonbackend.houses;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
class JoinCodeGenerator {

    private final Random random;


    public JoinCodeGenerator(Random random) {
        this.random = random;
    }

    String generateNewJoinCode() {
        int generatedCode = this.random.nextInt(9000) + 1000; //range 1000-9999
        return Integer.toString(generatedCode);
    }
}
