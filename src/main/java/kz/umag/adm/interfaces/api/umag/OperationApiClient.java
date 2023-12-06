package kz.umag.adm.interfaces.api.umag;

import feign.Param;
import feign.RequestLine;
import kz.umag.adm.dto.PageMapDto;

import java.time.LocalDate;

public interface OperationApiClient {

//    @RequestLine("GET /opr/internal/opr-by-day/opr-count?date={date}&offset={offset}&limit={limit}")
    @RequestLine("GET ?date={date}&offset={offset}&limit={limit}")
    PageMapDto<Integer, Boolean> getStoreActivityByDate(@Param LocalDate date, @Param int offset, @Param int limit);

}
