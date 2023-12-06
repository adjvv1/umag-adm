package kz.umag.adm.dto;

import kz.umag.adm.exception.CustomException;
import kz.umag.adm.model.OffsetJob;
import kz.umag.adm.service.OffsetJobService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Offset {

    private final OffsetJobService service;
    private final int jobId;


    public Value get() throws CustomException {
        OffsetJob job = service.get(jobId);
        return new Value(job.getOffset(), job.getLimit());
    }

    public void shift(int total) throws CustomException {
        OffsetJob job = service.get(jobId);
        int newOffset = job.getOffset() + job.getLimit();
        if (newOffset >= total) {
            service.updateStatus(jobId, OffsetJob.Status.EXECUTED);
        } else {
            service.updateOffset(jobId, newOffset);
        }
    }

    public boolean isCompleted() throws CustomException {
        OffsetJob job = service.get(jobId);
        OffsetJob.Status status = job.getStatus();
        return status == OffsetJob.Status.EXECUTED;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Value {
        private final int offset;
        private final int limit;
    }
}
