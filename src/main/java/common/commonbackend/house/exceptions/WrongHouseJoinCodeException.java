package common.commonbackend.house.exceptions;

public class WrongHouseJoinCodeException extends RuntimeException {
    public WrongHouseJoinCodeException() {
        super("Wrong house join code");
    }
}
