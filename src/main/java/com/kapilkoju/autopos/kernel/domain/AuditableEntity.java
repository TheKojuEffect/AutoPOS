package com.kapilkoju.autopos.kernel.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.kapilkoju.autopos.kernel.json.Views;
import com.kapilkoju.autopos.user.domain.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity
    extends VersionedEntity
    implements AuditedEntity {

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonView(Views.DateTimeAudited.class)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    @JsonView(Views.DateTimeAudited.class)
    private Instant lastModifiedDate;

    @CreatedBy
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "last_modified_by", nullable = false)
    @JsonIgnore
    private User lastModifiedBy;

    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    protected void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    protected void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public User getCreatedBy() {
        return createdBy;
    }

    protected void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    protected void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
