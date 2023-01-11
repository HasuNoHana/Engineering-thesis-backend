package common.commonbackend.users;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class UserDTO {
    Long id;
    String username;
    long points;
    long range;
    String image;

    public static UserDTO DTOFromUser(User user) {//TODO napraw to
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                40,
                100,
                "https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg"
        );
    }
}