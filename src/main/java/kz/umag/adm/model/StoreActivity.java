package kz.umag.adm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "store_activity", schema = "umag_adm")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StoreActivity extends EntityWithAutoIncrementId {
    @NotNull
    @Column(name = "store_id", updatable = false)
    private Integer storeId;
    @Column(name = "last_activity_date")
    private LocalDate lastActivityDate;
    @Column(name = "create_datetime", updatable = false)
    private LocalDateTime createDateTime;
    @Column(name = "update_datetime")
    private LocalDateTime updateDateTime;

    public StoreActivity(Integer storeId1) {
        this.storeId = storeId1;
    }

}
