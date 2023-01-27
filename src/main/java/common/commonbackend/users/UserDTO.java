package common.commonbackend.users;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class UserDTO {
    Long id;
    String username;
    long currentPoints;
    long weeklyContribution;
    String avatarImageUrl;
}