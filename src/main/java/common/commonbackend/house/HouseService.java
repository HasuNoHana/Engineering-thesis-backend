package common.commonbackend.house;

import common.commonbackend.house.exceptions.UserInformationNotFoundForUser;
import common.commonbackend.house.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.user.User;
import common.commonbackend.user.UserInformation;
import common.commonbackend.user.UserInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;

    private final JoinCodeGenerator joinCodeGenerator;

    private final UserInformationRepository userInformationRepository;

    public HouseEntity createHouseForUser(User user) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(joinCodeGenerator.generateNewJoinCode());
        Optional<UserInformation> userInformation = userInformationRepository.findById(user.getId());
        if (!userInformation.isPresent()) {
            throw new UserInformationNotFoundForUser(user);
        }
        houseEntity.addUser(userInformation.get());
        return houseRepository.save(houseEntity);
    }

    public void addUserToHouse(User user, String joinCode) {
        HouseEntity houseEntity = houseRepository.findByJoinCode(joinCode);
        if (houseEntity == null) {
            throw new WrongHouseJoinCodeException();
        }
        Optional<UserInformation> userInformation = userInformationRepository.findById(user.getId());
        if (!userInformation.isPresent()) {
            throw new UserInformationNotFoundForUser(user);
        }
        houseEntity.addUser(userInformation.get());
        houseRepository.save(houseEntity);

    }

    public HouseEntity getHouseForJoinCode(String joinCode) {
        return Optional.ofNullable(houseRepository.findByJoinCode(joinCode)) //TODO to wyjebac powinno zwracac house albo obsluzyc jakos blad
                .orElseGet(() -> createNewHouse(joinCode));
    }

    private HouseEntity createNewHouse(String joinCode) { //TODO to tez
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(joinCode);
        return houseRepository.save(houseEntity);
    }

    public HouseEntity createNewHouse() {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(joinCodeGenerator.generateNewJoinCode());
        return houseRepository.save(houseEntity);
    }

    public HouseEntity getHouseById(long houseId) {
        return houseRepository.findById(houseId).orElseThrow(() -> new RuntimeException("House not found"));
    }
}
