package common.commonbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.commonbackend.entities.Room;
import common.commonbackend.entities.User;
import common.commonbackend.repositories.RoomRepository;
import common.commonbackend.repositories.TaskRepository;
import common.commonbackend.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    public void shouldGetUser() throws Exception {
        //given
        User user = new User("zuza","haslo");

        //when
        when(userRepository.findByUsername("zuza")).thenReturn(user);

        //then
        mockMvc.perform(get("/api/user")
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}