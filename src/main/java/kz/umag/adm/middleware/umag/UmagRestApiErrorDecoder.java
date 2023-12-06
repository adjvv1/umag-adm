package kz.umag.adm.middleware.umag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import kz.umag.adm.dto.ExceptionInfoDto;
import kz.umag.adm.exception.CustomCriticalRuntimeException;
import kz.umag.adm.exception.CustomRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class UmagRestApiErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();
    private final String module;

    @Override
    public Exception decode(String methodKey,
                            Response response) {

        String errorText;
        try {
            Response.Body body = response.body();
            if (body != null) {
                errorText = new BufferedReader(new InputStreamReader(
                        body.asInputStream(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
            } else {
                throw new IOException("Пустое тело ответа");
            }
        } catch (IOException e) {
            return errorDecoder.decode(methodKey, response);
        }

        ExceptionInfoDto exceptionInfo = tryToGetErrorFromJson(errorText);
        if (exceptionInfo != null) {
            errorText = exceptionInfo.getMessage();
        }

        // User exception
        if (response.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
            log.warn(String.format("Exception in %s: %s", methodKey, errorText));
            return new CustomRuntimeException(errorText);
        }

        // Unhandled exception
        if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            log.error(String.format("%s exception: %s", module, errorText));
            return new CustomCriticalRuntimeException(
                    HttpStatus.INTERNAL_SERVER_ERROR, String.format("Непредвиденная ошибка в модуле %s", module)
            );
        }

        // Can return new IgnorableException(); and ignore exception

        // Normal case. Just forward to Feign Default error decoder
        return errorDecoder.decode(methodKey, response);
    }

    public UmagRestApiErrorDecoder(String moduleName) {
        super();
        this.module = moduleName;
    }

    private ExceptionInfoDto tryToGetErrorFromJson(String maybeJsonText) {
        if (maybeJsonText.startsWith("{")) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(maybeJsonText, ExceptionInfoDto.class);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        return null;
    }

}
