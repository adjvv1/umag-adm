package kz.umag.adm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class EntityWithAutoIncrementId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
