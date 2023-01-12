package common.commonbackend.houses;

import common.commonbackend.houses.exceptions.WrongHouseJoinCodeException;
import common.commonbackend.users.User;
import common.commonbackend.users.house_buddy.HouseBuddy;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
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
    private HouseBuddy houseBuddy;
    @Mock
    private HouseRepository houseRepository;
    @Mock
    JoinCodeGenerator joinCodeGenerator;
    private HouseService systemUnderTest;

    @BeforeEach
    void setUp() {
        systemUnderTest = new HouseService(houseRepository, joinCodeGenerator);
    }


    @Test
    void shouldCreateHouseForUser() {
        //given
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());
        when(joinCodeGenerator.generateNewJoinCode()).thenReturn(JOIN_CODE);
        when(user.getHouseBuddy()).thenReturn(houseBuddy);

        //when
        HouseEntity houseForUser = systemUnderTest.createHouseForUser(user);

        //then
        verify(houseRepository, times(1)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo(JOIN_CODE);
        assertThat(houseForUser.getHouseBuddies()).containsExactly(houseBuddy);
        assertThat(houseForUser.getRooms()).isEmpty();
    }

    @Test
    void shouldGetOrCreateHouseForExistingHouse() {
        //given
        HouseEntity house = new HouseEntity();
        house.setJoinCode(JOIN_CODE);
        when(houseRepository.findByJoinCode(JOIN_CODE)).thenReturn(house);

        //when
        HouseEntity houseForUser = systemUnderTest.getHouseForJoinCode(JOIN_CODE);

        //then
        verify(houseRepository, times(0)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo(JOIN_CODE);
    }

    @Test
    void shouldGetOrCreateHouseForNoExistingHouse() {
        //given
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());
        when(houseRepository.findByJoinCode(JOIN_CODE)).thenReturn(null);

        //when
        HouseEntity houseForUser = systemUnderTest.getHouseForJoinCode(JOIN_CODE);

        //then
        verify(houseRepository, times(1)).save(houseForUser);
        assertThat(houseForUser.getJoinCode()).isEqualTo(JOIN_CODE);
        assertThat(houseForUser.getHouseBuddies()).isEmpty();
        assertThat(houseForUser.getRooms()).isEmpty();
    }

    @Test
    void shouldAddUserToHouseIfCorrectJoinCode() {
        //given
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setJoinCode(JOIN_CODE);
        when(houseRepository.findByJoinCode(JOIN_CODE)).thenReturn(houseEntity);
        when(houseRepository.save(any())).thenAnswer(returnsFirstArg());
        when(user.getHouseBuddy()).thenReturn(houseBuddy);

        //when
        systemUnderTest.addUserToHouse(user, JOIN_CODE);

        //then
        verify(houseRepository, times(1)).save(houseEntity);
        assertThat(houseEntity.getHouseBuddies()).contains(houseBuddy);
    }

    @Test
    void shouldThrowExceptionIfWrongJoinCode() {
        //given
        when(houseRepository.findByJoinCode(JOIN_CODE)).thenReturn(null);

        //when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> systemUnderTest.addUserToHouse(user, JOIN_CODE);

        //then
        assertThatThrownBy(throwingCallable).isInstanceOf(WrongHouseJoinCodeException.class);
    }
}