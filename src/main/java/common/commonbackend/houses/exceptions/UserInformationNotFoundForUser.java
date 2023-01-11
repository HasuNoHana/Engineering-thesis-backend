package common.commonbackend.houses.exceptions;

import common.commonbackend.users.User;

public class UserInformationNotFoundForUser extends RuntimeException {
    public UserInformationNotFoundForUser(User user) {
        this("No userInformation found for user with id: " + user.getId());
    }

    public UserInformationNotFoundForUser() {
        this("Wrong house join code");
    }

    public UserInformationNotFoundForUser(String message) {
        super(message);
    }
}
