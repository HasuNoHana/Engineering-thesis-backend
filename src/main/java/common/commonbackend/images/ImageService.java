package common.commonbackend.images;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final ImageType IMAGE_TYPE_ROOM = ImageType.ROOM;
    private static final ImageType IMAGE_TYPE_AVATAR = ImageType.AVATAR;

    private final ImageRepository imageRepository;

    List<String> getAvatarImages() {
        return imageRepository
                .findImagesByImageType(IMAGE_TYPE_AVATAR)
                .stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
    }

    List<String> getRoomImages() {
        return imageRepository
                .findImagesByImageType(IMAGE_TYPE_ROOM)
                .stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
    }

    public void saveImage(Image image) {
        this.imageRepository.save(image);
    }
}
