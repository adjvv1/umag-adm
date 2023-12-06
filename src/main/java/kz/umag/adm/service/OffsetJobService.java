package kz.umag.adm.service;

import kz.umag.adm.exception.AppException;
import kz.umag.adm.exception.CustomException;
import kz.umag.adm.model.OffsetJob;
import kz.umag.adm.repository.OffsetJobRepository;
import kz.umag.adm.type.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional(rollbackFor = AppException.class)
@Service
@RequiredArgsConstructor
public class OffsetJobService {

    private static final String NOT_FOUND_ERROR = "Задание с заданным идентификатором не найдено";

    private final OffsetJobRepository repository;

    public OffsetJob create(String name, int offset, int limit) {
        OffsetJob job = new OffsetJob()
                .setName(name)
                .setOffset(offset)
                .setLimit(limit)
                .setStatus(OffsetJob.Status.NEW)
                .setExecutedDateTime(LocalDateTime.now());
        return repository.save(job);
    }

    public void updateOffset(int id, int offset) throws CustomException {
        OffsetJob job = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.UPDATING, NOT_FOUND_ERROR))
                .setOffset(offset)
                .setStatus(OffsetJob.Status.RUNNING)
                .setExecutedDateTime(LocalDateTime.now());
        repository.save(job);
    }

    public void updateStatus(int id, OffsetJob.Status status) throws CustomException {
        OffsetJob job = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.UPDATING, NOT_FOUND_ERROR))
                .setStatus(status)
                .setExecutedDateTime(LocalDateTime.now());
        repository.save(job);
    }

    public OffsetJob get(int id) throws CustomException {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.READING, NOT_FOUND_ERROR));
    }

    public Optional<OffsetJob> getByName(String name) {
        return repository.findByName(name);
    }
}
