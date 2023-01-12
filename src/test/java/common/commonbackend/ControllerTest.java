package common.commonbackend;

import common.commonbackend.houses.HouseRepository;
import common.commonbackend.houses.HouseService;
import common.commonbackend.images.ImageService;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.rooms.RoomService;
import common.commonbackend.tasks.TaskRepository;
import common.commonbackend.tasks.TaskService;
import common.commonbackend.users.UserService;
import common.commonbackend.users.house_buddy.HouseBuddyService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
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
    @MockBean
    protected RoomService roomService;
    @MockBean
    protected HouseBuddyService houseBuddyService;

    @MockBean
    protected ImageService imageService;
    @Autowired
    private MockMvc mockMvc;

    protected MockMvc getMocMvc() {
        return mockMvc;
    }

}
