package kz.umag.adm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "offset_job", schema = "umag_adm")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OffsetJob extends EntityWithAutoIncrementId {
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "offset")
    private Integer offset;
    @NotNull
    @Column(name = "size")
    private Integer limit;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "executed_datetime")
    private LocalDateTime executedDateTime;


    public enum Status {
        /**
         * Created with initial parameters
         */
        NEW,
        /**
         * Modified at least once and not finished yet
         */
        RUNNING,
        /**
         * Successfully executed
         */
        EXECUTED
    }
}
