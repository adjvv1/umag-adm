package kz.umag.adm.middleware;

import kz.umag.adm.dto.ExceptionInfoDto;
import kz.umag.adm.exception.*;
import kz.umag.adm.util.mapper.ExceptionMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.stream.Collectors;

/**
 * The handler for all {@link AppException} exceptions and other exceptions
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String LOG_PATTERN = "%s: %s";

    /**
     * Handles {@link UndeclaredThrowableException} exceptions, unwrap them and handle as {@link AppException} or as
     * common exception
     *
     * @param ex      the exception
     * @param request the request
     * @return response with data of unwrapped exception
     */
    @ExceptionHandler(UndeclaredThrowableException.class)
    protected ResponseEntity<ExceptionInfoDto> handleUndeclaredThrowableException(UndeclaredThrowableException ex,
                                                                                  WebRequest request) {

        var throwable = ex.getUndeclaredThrowable();
        if (throwable instanceof AppException appEx) {
            return handleAppException(appEx, request);
        }

        var errorData = getCommonErrorData(throwable, request);
        return ResponseEntity.internalServerError()
                .body(errorData);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,
                                                                 WebRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionInfoDto(HttpStatus.FORBIDDEN.value(), ex.getMessage()));
    }

    @ExceptionHandler(CustomRuntimeException.class)
    protected ExceptionInfoDto handleCustomRuntimeException(CustomRuntimeException ex,
                                                            WebRequest request) {

        log.warn(String.format(LOG_PATTERN, getRequestUri(request), ex.getMessage()));
        return new ExceptionInfoDto(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
    }

    @ExceptionHandler(CustomCriticalRuntimeException.class)
    protected ExceptionInfoDto handleCustomCriticalRuntimeException(CustomCriticalRuntimeException ex,
                                                                    WebRequest request) {

        log.error(String.format(LOG_PATTERN, getRequestUri(request), ex.getMessage()));
        return new ExceptionInfoDto(ex.getStatus().value(), ex.getMessage());
    }


    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ExceptionInfoDto handleCustomException(CustomException exception,
                                                     WebRequest request) {

        log.warn(String.format(LOG_PATTERN, getRequestUri(request), exception.getMessage()));
        return new ExceptionInfoDto(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage());
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request
    ) {
        log(exception, request);
        var fieldsErrors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("\r\n"));

        var responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(responseStatus)
                .body(new ExceptionInfoDto(responseStatus.value(), fieldsErrors));
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ExceptionInfoDto handleAuthException(AuthException exception,
                                                   WebRequest request) {

        log.warn(String.format(LOG_PATTERN, getRequestUri(request), exception.getMessage()));
        return new ExceptionInfoDto(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ExceptionInfoDto handleForbiddenException(ForbiddenException exception,
                                                        WebRequest request) {

        log.warn(String.format(LOG_PATTERN, getRequestUri(request), exception.getMessage()));
        return new ExceptionInfoDto(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    /**
     * Handles {@link AppException} exception
     *
     * @param exception the exception
     * @param request   the request
     * @return response with http code from the exception and with {@link ExceptionInfoDto} instance in body
     */
    @ExceptionHandler(AppException.class)
    private ResponseEntity<ExceptionInfoDto> handleAppException(AppException exception,
                                                                WebRequest request) {

        log(exception, request);
        var errorData = ExceptionMapper.toExceptionInfoDto(exception);
        return ResponseEntity.status(exception.getStatus())
                .body(errorData);
    }

    /**
     * Handles all exceptions which was not handled by other handlers
     *
     * @param exception the exception
     * @param request   the request
     * @return response with {@link HttpStatus#INTERNAL_SERVER_ERROR} http code and with {@link ExceptionInfoDto}
     * instance in body
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ExceptionInfoDto handleCommonException(Exception exception,
                                                   WebRequest request) {

        return getCommonErrorData(exception, request);
    }

    /**
     * Gets common error data for an exception
     *
     * @param exception the exception
     * @param request   the request
     * @return error data with {@link HttpStatus#INTERNAL_SERVER_ERROR} http code
     */
    private static ExceptionInfoDto getCommonErrorData(Throwable exception,
                                                       WebRequest request) {

        log(exception, request);
        return new ExceptionInfoDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    /**
     * Logs the exception in the follow form: <b>[request URI]:[exception's detailed message]</b>
     *
     * @param exception the exception
     * @param request   the request
     */
    private static void log(Throwable exception,
                            WebRequest request) {

        log.error(String.format(LOG_PATTERN, getRequestUri(request), exception.getMessage()), exception);
    }

    /**
     * Extracts request URI from the request
     *
     * @param request thr request
     * @return request URI as {@link String}
     */
    private static String getRequestUri(WebRequest request) {
        return ((ServletWebRequest) request).getRequest()
                .getRequestURI();
    }
}
