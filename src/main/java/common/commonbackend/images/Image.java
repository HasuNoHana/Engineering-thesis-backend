package common.commonbackend.images;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE")
@NoArgsConstructor
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "IMAGE_TYPE")
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    public Image(String imageUrl, ImageType imageType) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }
}
