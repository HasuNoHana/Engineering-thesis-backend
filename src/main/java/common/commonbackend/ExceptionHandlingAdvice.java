package common.commonbackend;

import common.commonbackend.house.exceptions.WrongHouseJoinCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlingAdvice {

    @ExceptionHandler(WrongHouseJoinCodeException.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return handleException(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> handleException(String message, HttpStatus httpStatus) {
        log.error("Error: " + message);
        return new ResponseEntity<>(message, httpStatus);
    }
}
