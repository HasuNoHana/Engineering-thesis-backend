package common.commonbackend.house;

import common.commonbackend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;

    public String createHouseForUser(User user) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(generateNewJoinCode());
        houseEntity.addUser(user);
        houseRepository.save(houseEntity);
        return houseEntity.getJoinCode();
    }

    private String generateNewJoinCode() {
        return "dupa";//NOSONAR TODO fix me
    }
}
