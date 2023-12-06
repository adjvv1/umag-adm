package kz.umag.adm.exception;

import org.springframework.http.HttpStatus;

/**
 * The exception can be thrown as some troubles with authentication were arisen. The exception throwing will return
 * response with code {@link HttpStatus#UNAUTHORIZED}
 */
public class AuthException extends AppException {
    public AuthException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
