package kz.umag.adm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomCriticalRuntimeException extends RuntimeException {

    private final HttpStatus status;

    public CustomCriticalRuntimeException(String message) {
        super(message);
        this.status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public CustomCriticalRuntimeException(HttpStatus httpStatus,
                                          String message) {

        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }

}

