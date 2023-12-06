package kz.umag.adm.service.api.umag;

import kz.umag.adm.dto.PageMapDto;
import kz.umag.adm.interfaces.api.umag.OperationApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.time.LocalDate;

import static kz.umag.adm.util.UmagApiUtil.createUmagApiClient;

@Slf4j
@Service
public class OperationService {

    private static final String MODULE_NAME = "Operation";

    @Value("${umag.api.operation.url}")
    private String url;

    private OperationApiClient client;

    @PostConstruct
    public void init() {
        this.client = createUmagApiClient(OperationApiClient.class, url, MODULE_NAME);
        log.info(String.format("%s address: %s", MODULE_NAME, url));
    }

    public PageMapDto<Integer, Boolean> getStoreActivityByDate(LocalDate date, int offset, int limit) {
        return client.getStoreActivityByDate(date, offset, limit);
    }

}
