package common.commonbackend.house;

import common.commonbackend.house.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.user.User;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@MockitoSettings
class HouseServiceTest {

    private final String JOIN_CODE = "1234";

    @Mock
    private User user;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    JoinCodeGenerator joinCodeGenerator;

    @Test
    void shouldCreateHouseForUser() { //TODO zrobic refactor tych testow
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());
        when(joinCodeGenerator.generateNewJoinCode()).thenReturn(JOIN_CODE);

        //when
        HouseEntity houseForUser = houseService.createHouseForUser(user);

        //then
        verify(houseRepository, times(1)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo(JOIN_CODE);
        assertThat(houseForUser.getUsers()).containsExactly(user);
        assertThat(houseForUser.getRooms()).isEmpty();
    }

    @Test
    void shouldGetOrCreateHouseForExistingHouse() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        HouseEntity house = new HouseEntity();
        house.setJoinCode(JOIN_CODE);
        when(houseRepository.findByJoinCode(JOIN_CODE)).thenReturn(house);

        //when
        HouseEntity houseForUser = houseService.getHouseForJoinCode(JOIN_CODE);

        //then
        verify(houseRepository, times(0)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo(JOIN_CODE);
    }

    @Test
    void shouldGetOrCreateHouseForNoExistingHouse() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());
        when(houseRepository.findByJoinCode(JOIN_CODE)).thenReturn(null);

        //when
        HouseEntity houseForUser = houseService.getHouseForJoinCode(JOIN_CODE);

        //then
        verify(houseRepository, times(1)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo(JOIN_CODE);
        assertThat(houseForUser.getUsers()).isEmpty();
        assertThat(houseForUser.getRooms()).isEmpty();
    }

    @Test
    void shouldAddUserToHouseIfCorrectJoinCode() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(JOIN_CODE);
        when(houseRepository.findByJoinCode(JOIN_CODE)).thenReturn(houseEntity);
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());

        //when
        houseService.addUserToHouse(user, JOIN_CODE);

        //then
        verify(houseRepository, times(1)).save(houseEntity);
        assertThat(houseEntity.getUsers()).contains(user);
    }

    @Test
    void shouldThrowExceptionIfWrongJoinCode() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        when(houseRepository.findByJoinCode(JOIN_CODE)).thenReturn(null);

        //when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> houseService.addUserToHouse(user, JOIN_CODE);

        //then
        assertThatThrownBy(throwingCallable).isInstanceOf(WrongHouseJoinCodeException.class);
    }
}