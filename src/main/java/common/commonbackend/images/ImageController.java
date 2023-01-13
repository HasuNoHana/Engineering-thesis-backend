package common.commonbackend.images;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;


    @GetMapping(path = "/avatarImages")
    public ResponseEntity<Iterable<String>> getAvatarImages() {
        List<String> imageUrls = imageService.getAvatarImages();
        return new ResponseEntity<>(imageUrls, HttpStatus.OK);
    }

    @GetMapping(path = "/roomImages")
    public ResponseEntity<Iterable<String>> getRoomImages() {
        List<String> imageUrls = imageService.getRoomImages();
        return new ResponseEntity<>(imageUrls, HttpStatus.OK);
    }
}
