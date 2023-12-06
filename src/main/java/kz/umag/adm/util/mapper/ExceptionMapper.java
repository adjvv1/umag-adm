package kz.umag.adm.util.mapper;

import kz.umag.adm.dto.ExceptionInfoDto;
import kz.umag.adm.exception.AppException;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ExceptionMapper {

    public static ExceptionInfoDto toExceptionInfoDto(AppException exception) {
        return new ExceptionInfoDto()
                .setStatus(exception.getStatus() == null ? null : exception.getStatus().value())
                .setMessage(exception.getMessage());
    }
}
