package common.commonbackend.rooms;

import common.commonbackend.house.HouseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings
class RoomServiceTest {
    private static final String ROOM_IMAGE = "url";
    private static final String ROOM_NAME = "Kitchen";
    @Mock
    private RoomRepository roomRepository;
    private RoomService systemUnderTest;

    @BeforeEach
    void setUp() {
        systemUnderTest = new RoomService(roomRepository);
    }

    @Test
    void shouldCreateRoom() {
        //given
        HouseEntity house = new HouseEntity();
        RoomDTO roomDTO = new RoomDTO(ROOM_NAME, ROOM_IMAGE);
        when(roomRepository.save(any())).thenAnswer(returnsFirstArg());

        //when
        Room actual = systemUnderTest.createRoom(roomDTO, house);

        //then
        assertThat(actual)
                .extracting(
                        Room::getName,
                        Room::getImage,
                        Room::getHouse)
                .containsExactly(
                        ROOM_NAME,
                        ROOM_IMAGE,
                        house);
        verify(roomRepository, times(1)).save(any());
    }
}