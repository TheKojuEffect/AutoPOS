package com.kapilkoju.autopos.kernel.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.kapilkoju.autopos.kernel.json.Views;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class VersionedEntity
    extends AbstractBaseEntity
    implements Versioned {

    @Version
    @Column(name = "version")
    @JsonView(Views.Versioned.class)
    private Long version;

    @Override
    public Long getVersion() {
        return version;
    }

    protected void setVersion(final Long version) {
        this.version = version;
    }

}
