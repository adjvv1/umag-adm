package kz.umag.adm.job;

import kz.umag.adm.dto.Offset;
import kz.umag.adm.dto.PageMapDto;
import kz.umag.adm.exception.CustomException;
import kz.umag.adm.service.OffsetInitializer;
import kz.umag.adm.service.StoreActivityService;
import kz.umag.adm.service.api.umag.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

@Slf4j
@Service
@ConditionalOnProperty(name = "umag-adm.job.store-activity.enabled")
@RequiredArgsConstructor
public class StoreActivityJob {

    private final StoreActivityService storeActivityService;
    private final OperationService operationService;
    private final OffsetInitializer initializer;

    @Scheduled(cron = "* */5 * * * *")
    @SchedulerLock(name = "update-store-activity", lockAtLeastFor = "5m", lockAtMostFor = "30m")
    public void update() throws CustomException {
        log.error("start");
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Offset offset = initializer.getOffset(getOffsetInstanceName(yesterday));
        while (!offset.isCompleted()) {
            Offset.Value value = offset.get();
            log.error(String.valueOf(value.getOffset()));
            PageMapDto<Integer, Boolean> pageDto;
            try {
                pageDto = operationService.getStoreActivityByDate(yesterday, value.getOffset(), value.getLimit());
            } catch (Exception ex) {
                throw new CustomException("External service error: " + ex.getMessage());
            }
            storeActivityService.updateByDate(pageDto.getContent(), yesterday);
            offset.shift(pageDto.getTotalElements());
        }
    }

    private String getOffsetInstanceName(LocalDate date) {
        return new StringJoiner("_")
                .add("STORE_ACTIVITY")
                .add(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .toString();
    }
}

