package kz.umag.adm.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AppException {

    public ForbiddenException(String inMessage) {
        super(HttpStatus.FORBIDDEN, inMessage);
    }

}
