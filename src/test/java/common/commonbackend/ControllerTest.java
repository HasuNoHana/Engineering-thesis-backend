package common.commonbackend;

import common.commonbackend.house.HouseRepository;
import common.commonbackend.house.HouseService;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.TaskRepository;
import common.commonbackend.tasks.TaskService;
import common.commonbackend.user.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public abstract class ControllerTest {

    @MockBean
    protected TaskRepository taskRepository;

    @MockBean
    @Qualifier("userService")
    protected UserDetailsService userDetailsService;

    @MockBean
    protected HouseRepository houseRepository;

    @MockBean
    protected TaskService taskService;
    @MockBean
    protected UserService userService;
    @MockBean
    protected HouseService houseService;

    @MockBean
    protected RoomRepository roomRepository;

    @MockBean
    protected ControllerHelper controllerHelper;

    @Autowired
    private MockMvc mockMvc;

    protected MockMvc getMocMvc(){
        return mockMvc;
    }

}
