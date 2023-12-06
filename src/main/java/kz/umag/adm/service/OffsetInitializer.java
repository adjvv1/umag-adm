package kz.umag.adm.service;

import kz.umag.adm.dto.Offset;
import kz.umag.adm.model.OffsetJob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OffsetInitializer {

    private static final int DEFAULT_INITIAL_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 50;

    private final OffsetJobService service;

    public Offset getOffset(String name) {
        OffsetJob job = service.getByName(name)
                .orElseGet(() -> service.create(name, DEFAULT_INITIAL_OFFSET, DEFAULT_LIMIT));
        return new Offset(service, job.getId());
    }
}
