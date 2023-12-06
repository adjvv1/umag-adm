package kz.umag.adm.service;

import kz.umag.adm.exception.AppException;
import kz.umag.adm.model.StoreActivity;
import kz.umag.adm.repository.StoreActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Transactional(rollbackFor = AppException.class)
@Service
@RequiredArgsConstructor
public class StoreActivityService {

    private final StoreActivityRepository repository;

    public void updateByDate(Map<Integer, Boolean> actual, LocalDate date) {
        for (Map.Entry<Integer, Boolean> entry : actual.entrySet()) {
            int storeId = entry.getKey();
            boolean active = entry.getValue();
            StoreActivity storeActivity = repository.findByStoreId(storeId)
                    .orElseGet(() -> new StoreActivity(storeId))
                    .setUpdateDateTime(LocalDateTime.now());
            if (active) {
                storeActivity.setLastActivityDate(date);
            }
            repository.save(storeActivity);
        }
    }

}
