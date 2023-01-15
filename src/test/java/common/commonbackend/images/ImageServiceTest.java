package common.commonbackend.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.Mockito.when;

@MockitoSettings
class ImageServiceTest {
    private static final ImageType IMAGE_TYPE_ROOM = ImageType.ROOM;
    private static final ImageType IMAGE_TYPE_AVATAR = ImageType.AVATAR;
    private static final String IMAGE_URL = "url1";
    private ImageService systemUnderTest;
    @Mock
    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        systemUnderTest = new ImageService(imageRepository);
    }

    @Test
    void shouldGetAvatarImages() {
        //given
        List<Image> images = List.of(new Image(IMAGE_URL, IMAGE_TYPE_AVATAR));
        when(imageRepository.findImagesByImageType(IMAGE_TYPE_AVATAR)).thenReturn(images);

        //when
        List<String> expected = systemUnderTest.getAvatarImages();

        //then
        assertLinesMatch(expected, images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
    }

    @Test
    void shouldGetRoomImages() {
        //given
        List<Image> images = List.of(new Image(IMAGE_URL, IMAGE_TYPE_ROOM));
        when(imageRepository.findImagesByImageType(IMAGE_TYPE_ROOM)).thenReturn(images);

        //when
        List<String> expected = systemUnderTest.getRoomImages();

        //then
        assertLinesMatch(expected, images.stream().map(Image::getImageUrl).collect(Collectors.toList()));
    }
}
