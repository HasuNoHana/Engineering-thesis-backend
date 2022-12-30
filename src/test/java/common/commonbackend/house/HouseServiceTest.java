package common.commonbackend.house;

import common.commonbackend.entities.User;
import common.commonbackend.house.exceptions.WrongHouseJoinCodeException;
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

    @Mock
    private User user;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    JoinCodeGenerator joinCodeGenerator;

    @Test
    void shouldCreateHouseForUser() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());
        when(joinCodeGenerator.generateNewJoinCode()).thenReturn("1234");

        //when
        HouseEntity houseForUser = houseService.createHouseForUser(user);

        //then
        verify(houseRepository, times(1)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo("1234");
        assertThat(houseForUser.getUsers()).containsExactly(user);
        assertThat(houseForUser.getRooms()).isEmpty();
    }

    @Test
    void shouldGetOrCreateHouseForExistingHouse() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        String joinCode = "1234";
        HouseEntity house = new HouseEntity();
        house.setJoinCode(joinCode);
        when(houseRepository.findByJoinCode(joinCode)).thenReturn(house);

        //when
        HouseEntity houseForUser = houseService.getOrCreateHouse(joinCode);

        //then
        verify(houseRepository, times(0)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo(joinCode);
    }

    @Test
    void shouldGetOrCreateHouseForNoExistingHouse() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        String joinCode = "1234";
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());
        when(houseRepository.findByJoinCode(joinCode)).thenReturn(null);

        //when
        HouseEntity houseForUser = houseService.getOrCreateHouse(joinCode);

        //then
        verify(houseRepository, times(1)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo(joinCode);
        assertThat(houseForUser.getUsers()).isEmpty();
        assertThat(houseForUser.getRooms()).isEmpty();
    }

    @Test
    void shouldAddUserToHouseIfCorrectJoinCode() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        String joinCode = "1234";
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(joinCode);
        when(houseRepository.findByJoinCode(joinCode)).thenReturn(houseEntity);
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());

        //when
        houseService.addUserToHouse(user, joinCode);

        //then
        verify(houseRepository, times(1)).save(houseEntity);
        assertThat(houseEntity.getUsers()).contains(user);
    }

    @Test
    void shouldThrowExceptionIfWrongJoinCode() {
        //given
        HouseService houseService = new HouseService(houseRepository, joinCodeGenerator);
        String joinCode = "1234";
        when(houseRepository.findByJoinCode(joinCode)).thenReturn(null);

        //when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> houseService.addUserToHouse(user, joinCode);

        //then
        assertThatThrownBy(throwingCallable).isInstanceOf(WrongHouseJoinCodeException.class);
    }
}