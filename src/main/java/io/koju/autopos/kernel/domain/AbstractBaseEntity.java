package io.koju.autopos.kernel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractBaseEntity
    extends AbstractEntity<Long>
    implements BaseEntity {

    @JsonIgnore
    @Override
    public final Long getIdentity() {
        return getId();
    }

}
