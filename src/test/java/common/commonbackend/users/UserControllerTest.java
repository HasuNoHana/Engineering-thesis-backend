package common.commonbackend.users;

import common.commonbackend.ControllerTest;
import common.commonbackend.houses.HouseEntity;
import common.commonbackend.users.house_buddy.HouseBuddy;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.List;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockitoSettings
class UserControllerTest extends ControllerTest {

    public static final long USER_ID = 1L;
    public static final String USERNAME = "zuza";
    public static final Long FIREWOOD_STACK_SIZE = 40L;

    public static final long DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION = 100L;
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg";
    public static final String USERNAME2 = "filip";
    public static final int FIREWOOD_STACK_SIZE_2 = 20;
    public static final int WEEKLY_FIREWOOD_CONTRIBUTION_2 = 30;
    public static final String IMAGE_2 = "url2";
    private static final String PASSWORD = "password";

    @Mock
    User user;

    @Mock
    HouseEntity house;

    @Mock
    HouseBuddy houseBuddy;

    @Test
    @SneakyThrows
    void shouldGetUser() {
        //given
        HouseEntity house = new HouseEntity();
        HouseBuddy houseBuddy = new HouseBuddy(FIREWOOD_STACK_SIZE, DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION, DEFAULT_IMAGE, house);
        User user = new User(USERNAME, PASSWORD, houseBuddy);
        when(controllerHelper.getMyUser()).thenReturn(user);

        //when
        getMocMvc().perform(get("/api/currentUserData"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(houseBuddy)))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void shouldGetUsers() {
        //given
        User user1 = new User(USERNAME, "haslo", houseBuddy);
        User user2 = new User(USERNAME2, "password", houseBuddy);
        List<User> users = List.of(user1,
                user2);
        UserDTO userDTO1 = new UserDTO(USER_ID, USERNAME, FIREWOOD_STACK_SIZE,
                DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION, DEFAULT_IMAGE);
        UserDTO userDTO2 = new UserDTO(2L, USERNAME2, FIREWOOD_STACK_SIZE_2,
                WEEKLY_FIREWOOD_CONTRIBUTION_2, IMAGE_2);
        List<UserDTO> usersDTOs = List.of(userDTO1,
                userDTO2);
        when(controllerHelper.getMyHouse()).thenReturn(house);
        when(userService.getUsersForHouse(house)).thenReturn(users);
        when(houseBuddyService.createUserDTOsFromHouseBuddies(users)).thenReturn(usersDTOs);

        //when
        getMocMvc().perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(usersDTOs)))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void shouldEditUser() {
        //given
        UserDTO userDTO = new UserDTO(USER_ID, USERNAME, FIREWOOD_STACK_SIZE, DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION,
                DEFAULT_IMAGE);
        when(userService.editUser(USER_ID, userDTO)).thenReturn(user);
        HouseEntity house = new HouseEntity();
        HouseBuddy houseBuddy = new HouseBuddy(FIREWOOD_STACK_SIZE, DEFAULT_WEEKLY_FIREWOOD_CONTRIBUTION,
                DEFAULT_IMAGE, house);
        when(user.getHouseBuddy()).thenReturn(houseBuddy);

        //when
        getMocMvc().perform(post("/api/editUser?id=" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(houseBuddy)))
                .andDo(print());

        //then
        verify(userService, times(1)).editUser(USER_ID, userDTO);
    }

}