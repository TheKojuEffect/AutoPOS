package io.koju.autopos.shared.domain;

import javax.persistence.Column;
import javax.persistence.Version;

public abstract class VersionedEntity
    extends AbstractEntity
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
