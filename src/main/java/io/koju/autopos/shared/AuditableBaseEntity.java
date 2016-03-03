package io.koju.autopos.shared;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableBaseEntity<U>
    extends BaseEntity
    implements AuditedEntity<U> {

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private U createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "last_modified_by", nullable = false)
    private U lastModifiedBy;


    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    protected void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    protected void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public U getCreatedBy() {
        return createdBy;
    }

    protected void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    protected void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
