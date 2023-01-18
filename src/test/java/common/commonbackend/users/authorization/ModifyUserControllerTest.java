package common.commonbackend.users.authorization;

import common.commonbackend.ControllerTest;
import common.commonbackend.users.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModifyUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockitoSettings
class ModifyUserControllerTest extends ControllerTest {

    private static final String NEW_PASSWORD = "new";
    private static final String CURRENT_PASSWORD = "current";
    private static final ModifyUserController.Passwords passwords =
            new ModifyUserController.Passwords(CURRENT_PASSWORD, NEW_PASSWORD);
    @Mock
    User user;

    @Test
    @SneakyThrows
    void shouldDeleteUser() {
        //given
        when(controllerHelper.getMyUser()).thenReturn(user);

        //when
        getMocMvc().perform(delete("/api/deleteCurrentUser"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(userService, times(1)).deleteUser(user);
    }

    @Test
    @SneakyThrows
    void shouldChangePassword() {
        //given
        when(controllerHelper.getMyUser()).thenReturn(user);

        //when
        getMocMvc().perform(post("/api/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passwords)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(userService, times(1))
                .changePassword(passwords.getCurrentPassword(), passwords.getNewPassword(), user);
    }

}