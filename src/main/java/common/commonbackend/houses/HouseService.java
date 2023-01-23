package common.commonbackend.houses;

import common.commonbackend.houses.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;
    private final JoinCodeGenerator joinCodeGenerator;

    HouseEntity createHouseForUser(User user) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(joinCodeGenerator.generateNewJoinCode());
        houseEntity.addHouseBuddy(user.getHouseBuddy());
        return houseRepository.save(houseEntity);
    }

    void addUserToHouse(User user, String joinCode) {
        HouseEntity houseEntity = houseRepository.findByJoinCode(joinCode);
        if (houseEntity == null) {
            throw new WrongHouseJoinCodeException();
        }
        houseEntity.addHouseBuddy(user.getHouseBuddy());
        houseRepository.save(houseEntity);
    }

    public HouseEntity getHouseForJoinCode(String joinCode) {
        return Optional.ofNullable(houseRepository.findByJoinCode(joinCode))
                .orElseGet(() -> createNewHouse(joinCode));
    }

    private HouseEntity createNewHouse(String joinCode) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(joinCode);
        return houseRepository.save(houseEntity);
    }

    public HouseEntity createNewHouse() {
        HouseEntity houseEntity = new HouseEntity();
        String joinCode = generateDifferentJoinCode();
        houseEntity.setJoinCode(joinCode);
        return houseRepository.save(houseEntity);
    }

    private String generateDifferentJoinCode() {
        List<String> joinCodes = new ArrayList<>();
        houseRepository.findAll().forEach(houseEntity -> joinCodes.add(houseEntity.getJoinCode()));
        String joinCode = joinCodeGenerator.generateNewJoinCode();
        while (joinCodes.contains(joinCode)) {
            joinCode = joinCodeGenerator.generateNewJoinCode();
        }
        return joinCode;
    }
}
