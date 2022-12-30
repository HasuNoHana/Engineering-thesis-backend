package common.commonbackend.house;

import common.commonbackend.entities.User;
import common.commonbackend.house.exceptions.WrongHouseJoinCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;

    private final JoinCodeGenerator joinCodeGenerator;

    public HouseEntity createHouseForUser(User user) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(joinCodeGenerator.generateNewJoinCode());
        houseEntity.addUser(user);
        return houseRepository.save(houseEntity);
    }

    public void addUserToHouse(User user, String joinCode) {
        HouseEntity houseEntity = houseRepository.findByJoinCode(joinCode);
        if (houseEntity == null) {
            throw new WrongHouseJoinCodeException();
        }
        houseEntity.addUser(user);
        houseRepository.save(houseEntity);

    }

    public HouseEntity getOrCreateHouse(String joinCode) {
        return Optional.ofNullable(houseRepository.findByJoinCode(joinCode))
                .orElseGet(() -> createNewHouse(joinCode));
    }

    private HouseEntity createNewHouse(String joinCode) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(joinCode);
        return houseRepository.save(houseEntity);
    }
}
