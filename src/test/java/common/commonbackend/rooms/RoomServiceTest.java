package common.commonbackend.rooms;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.tasks.TaskService;
import lombok.SneakyThrows;
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
    private static final HouseEntity HOUSE = new HouseEntity();
    private static final String ROOM_IMAGE = "url";
    private static final String ROOM_NAME = "Kitchen";
    private static final long ID = 1L;
    private static final String UPDATED_ROOM_NAME = "updatedRoomName";
    private static final String UPDATED_ROOM_IMAGE = "updatedRoomImage";
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private TaskService taskService;
    private RoomService systemUnderTest;

    @BeforeEach
    void setUp() {
        systemUnderTest = new RoomService(roomRepository, taskService);
    }

    @Test
    void shouldCreateRoom() {
        //given
        RoomDTO roomDTO = new RoomDTO(1L, ROOM_NAME, ROOM_IMAGE, 0);
        when(roomRepository.save(any())).thenAnswer(returnsFirstArg());

        //when
        RoomDTO actual = systemUnderTest.createRoom(roomDTO, HOUSE);

        //then
        assertThat(actual)
                .extracting(
                        RoomDTO::getName,
                        RoomDTO::getImage)
                .containsExactly(
                        ROOM_NAME,
                        ROOM_IMAGE);
        verify(roomRepository, times(1)).save(any());
    }

    @Test
    void shouldUpdateRoom() {
        //given
        RoomDTO roomDTO = new RoomDTO(1L, UPDATED_ROOM_NAME, UPDATED_ROOM_IMAGE, 0);
        Room oldRoom = new Room(1L, ROOM_NAME, ROOM_IMAGE, HOUSE);
        when(roomRepository.getRoomByIdAndHouse(ID, HOUSE)).thenReturn(oldRoom);
        when(roomRepository.save(any())).thenAnswer(returnsFirstArg());

        //when
        RoomDTO actual = systemUnderTest.updateRoom(roomDTO, HOUSE);

        //then
        assertThat(actual)
                .extracting(
                        RoomDTO::getName,
                        RoomDTO::getImage)
                .containsExactly(
                        UPDATED_ROOM_NAME,
                        UPDATED_ROOM_IMAGE);
        verify(roomRepository, times(1)).save(any());
    }

    @Test
    @SneakyThrows
    void shouldDeleteRoom() {
        //given
        HouseEntity house = new HouseEntity();
        Room room = new Room(ROOM_NAME, ROOM_IMAGE, house);
        when(roomRepository.getRoomByIdAndHouse(ID, house)).thenReturn(room);

        //when
        Room actual = systemUnderTest.deleteRoom(ID, house);

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
        verify(roomRepository, times(1)).delete(any());
    }
}