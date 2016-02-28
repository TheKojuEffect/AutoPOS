package io.koju.autopos.shared;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@MappedSuperclass
public abstract class AuditableBaseEntity<U>
    extends BaseEntity
    implements AuditableEntity<U> {

    @CreatedBy
    @ManyToOne(fetch = LAZY)
    private U createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    @ManyToOne(fetch = LAZY)
    private U lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Override
    public U getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
