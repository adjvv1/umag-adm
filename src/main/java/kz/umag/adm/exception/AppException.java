package kz.umag.adm.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The abstract class is for all application's business exceptions. Default response status for all inheritors of the
 * class is {@link AppException#DEFAULT_RESPONSE_STATUS}
 */
@Getter
public abstract class AppException extends Exception {
    protected static final HttpStatus DEFAULT_RESPONSE_STATUS = HttpStatus.UNPROCESSABLE_ENTITY;

    private final HttpStatus status;

    /**
     * Default constructor. Used by exceptions throwing of which should return
     * {@link AppException#DEFAULT_RESPONSE_STATUS}
     *
     * @param inMessage the message of the exception
     */
    protected AppException(String inMessage) {
        this(DEFAULT_RESPONSE_STATUS, inMessage);
    }

    /**
     * Constructor for exceptions throwing of which returns http code other than
     * {@link AppException#DEFAULT_RESPONSE_STATUS}
     *
     * @param inStatus  the http status of the response which will be returned after exception throwing
     * @param inMessage the message of the exception
     */
    protected AppException(HttpStatus inStatus, String inMessage) {
        super(inMessage);
        this.status = inStatus;
    }
}
