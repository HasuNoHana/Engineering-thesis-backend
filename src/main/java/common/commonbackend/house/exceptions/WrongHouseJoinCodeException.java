package common.commonbackend.house.exceptions;

public class WrongHouseJoinCodeException extends RuntimeException {
    public WrongHouseJoinCodeException() {
        this("Wrong house join code");
    }

    public WrongHouseJoinCodeException(String message) {
        super(message);
    }
}
