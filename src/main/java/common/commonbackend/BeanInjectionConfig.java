package common.commonbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.Random;

@Configuration
public class BeanInjectionConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public Random random() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }

}
