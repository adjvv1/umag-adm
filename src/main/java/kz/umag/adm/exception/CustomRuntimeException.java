package kz.umag.adm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomRuntimeException extends RuntimeException {

    public CustomRuntimeException(String message) {
        super(message);
    }

}
