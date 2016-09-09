package io.koju.autopos.kernel.domain;

import com.fasterxml.jackson.annotation.JsonView;
import io.koju.autopos.kernel.json.Views;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
public abstract class VersionedEntity<ID extends Serializable>
        extends AbstractEntity<ID>
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
