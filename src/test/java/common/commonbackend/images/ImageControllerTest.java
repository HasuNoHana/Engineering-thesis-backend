package common.commonbackend.images;

import common.commonbackend.ControllerTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static common.commonbackend.TestObjectMapperHelper.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockitoSettings
class ImageControllerTest extends ControllerTest {

    @Test
    @SneakyThrows
    void shouldGetRoomImages() {
        //given
        List<String> roomImages = List.of("https://upload.wikimedia.org/wikipedia/commons/5/59/Kitchen_life_in_the_18nt_cen.JPG",
                "https://upload.wikimedia.org/wikipedia/commons/d/dc/Kitchen_Renovation_Marlton_New_Jersey.jpg");
        when(imageService.getRoomImages()).thenReturn(roomImages);

        //when
        getMocMvc().perform(get("/api/roomImages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(roomImages)));
    }

    @Test
    @SneakyThrows
    void shouldGetAvatarImages() {
        //given
        List<String> avatarImages = List.of("https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg",
                "https://upload.wikimedia.org/wikipedia/commons/3/35/Red-simple-heart-symbol-only.png");
        when(imageService.getAvatarImages()).thenReturn(avatarImages);

        //when
        getMocMvc().perform(get("/api/avatarImages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(avatarImages)));
    }

}