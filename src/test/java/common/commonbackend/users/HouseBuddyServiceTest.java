package common.commonbackend.users;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.users.house_buddy.HouseBuddy;
import common.commonbackend.users.house_buddy.HouseBuddyRepository;
import common.commonbackend.users.house_buddy.HouseBuddyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@MockitoSettings
class HouseBuddyServiceTest {

    private static final long DEFAULT_FIREWOOD_STACK_SIZE = 0L;
    private static final long DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION = 100L;
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg";
    private static final String USERNAME = "username";
    private static final long USER_ID = 1L;
    private static final long FIREWOOD_STACK_SIZE = 40L;
    private static final long WEEKLY_POINTS_CONTRIBUTION = 100L;
    private static final String IMAGE = "image";
    private static final long CURRENT_PRICE = 30L;

    @Mock
    private HouseEntity house;
    @Mock
    private User user;
    @Mock
    private HouseBuddyRepository houseBuddyRepository;
    private HouseBuddyService systemUnderTest;

    @BeforeEach
    void setUp() {
        systemUnderTest = new HouseBuddyService(houseBuddyRepository);
    }

    @Test
    void shouldGetDefaultHouseBuddy() {
        //when
        HouseBuddy houseBuddy = systemUnderTest.getDefaultHouseBuddy(house);

        //then
        assertEquals(DEFAULT_FIREWOOD_STACK_SIZE, houseBuddy.getFirewoodStackSize());
        assertEquals(DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION, houseBuddy.getWeeklyFirewoodContribution());
        assertEquals(DEFAULT_IMAGE, houseBuddy.getAvatarImageUrl());
        assertEquals(house, houseBuddy.getHouse());
    }

    @Test
    void shouldCreateUserDTOFromHouseBuddy() {
        //given
        HouseBuddy houseBuddy = new HouseBuddy(FIREWOOD_STACK_SIZE, WEEKLY_POINTS_CONTRIBUTION, IMAGE,
                house, user);
        when(user.getUsername()).thenReturn(USERNAME);
        when(user.getId()).thenReturn(USER_ID);

        //when
        UserDTO userDTO = systemUnderTest.createUserDTOFromHouseBuddy(houseBuddy);

        //then
        assertEquals(USER_ID, userDTO.getId());
        assertEquals(USERNAME, userDTO.getUsername());
        assertEquals(FIREWOOD_STACK_SIZE, userDTO.getPoints());
        assertEquals(WEEKLY_POINTS_CONTRIBUTION, userDTO.getRange());
        assertEquals(IMAGE, userDTO.getImage());
    }

    @Test
    void shouldAddPointsToHouseBuddy() {
        //given
        HouseBuddy houseBuddy = new HouseBuddy(FIREWOOD_STACK_SIZE, WEEKLY_POINTS_CONTRIBUTION, IMAGE,
                house, user);
        when(user.getHouseBuddy()).thenReturn(houseBuddy);
        when(houseBuddyRepository.getHouseBuddyById(any())).thenReturn(houseBuddy);
        when(houseBuddyRepository.save(any())).thenReturn(houseBuddy);

        //when
        User actual = systemUnderTest.addPointsToHouseBuddy(user, CURRENT_PRICE);

        //then
        assertThat(actual.getHouseBuddy().getFirewoodStackSize()).isEqualTo(CURRENT_PRICE + FIREWOOD_STACK_SIZE);
    }

    @Test
    void shouldSubstractPointsToHouseBuddy() {
        //given
        HouseBuddy houseBuddy = new HouseBuddy(FIREWOOD_STACK_SIZE, WEEKLY_POINTS_CONTRIBUTION, IMAGE,
                house, user);
        when(user.getHouseBuddy()).thenReturn(houseBuddy);
        when(houseBuddyRepository.getHouseBuddyById(any())).thenReturn(houseBuddy);
        when(houseBuddyRepository.save(any())).thenReturn(houseBuddy);

        //when
        User actual = systemUnderTest.substractPointsFromHouseBuddy(user, CURRENT_PRICE);

        //then
        assertThat(actual.getHouseBuddy().getFirewoodStackSize()).isEqualTo(FIREWOOD_STACK_SIZE - CURRENT_PRICE);
    }

}