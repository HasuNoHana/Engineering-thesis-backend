package common.commonbackend.controllers;

import common.commonbackend.ControllerTest;
import common.commonbackend.entities.User;
import common.commonbackend.house.HouseEntity;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static common.commonbackend.controllers.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest extends ControllerTest {

    @Test
    public void shouldGetUser() throws Exception {
        //given
        HouseEntity house = new HouseEntity();
        User user = new User("zuza","haslo", house);

        //when

        //then
        getMocMvc().perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
//                .andExpect(content().contentTypeCompatibleWith("application/json"))
//                .andExpect(jsonPath("$.principal.username").value(user.getUsername()))
//                .andExpect(jsonPath("$.principal.password").value(user.getPassword()))
//                .andExpect(jsonPath("$.username",containsString(user.getUsername()))
//                .andExpect(content().json(asJsonString(user)));
//                .andDo(print());
    }

    @Test
    public void shouldCreateUser() throws Exception {
        //given
        String joinCode = "1234";
        String username = "zuza";
        String password = "haslo";
        HouseEntity house = new HouseEntity();
        house.setJoinCode(joinCode);
        UserSignup userSignup = new UserSignup(username,password, joinCode);
        User user = new User(username,password, house);
        when(userService.createUser(username, password, joinCode)).thenReturn(user);

        //then
        getMocMvc().perform(post("/api/create-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userSignup)))
                .andExpect(status().isOk());

        verify(userService, times(1)).createUser(username,password,joinCode);
    }


}