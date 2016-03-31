package io.koju.autopos.kernel.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
public abstract class VersionedEntity<ID extends Serializable>
    extends AbstractEntity<ID>
    implements Versioned {

    @Version
    @Column(name = "opt_lock")
    private Long version;

    @Override
    public Long getVersion() {
        return version;
    }

    protected void setVersion(final Long version) {
        this.version = version;
    }

}
