package com.kapilkoju.autopos.kernel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.ClassUtils;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable>
    implements IdentifiablePersistable<ID> {

    private static final long serialVersionUID = -5554308939380869754L;

    @Transient
    @JsonIgnore
    public boolean isNew() {
        return null == getIdentity();
    }


    @Override
    public String toString() {
        return String.format("Entity of type %s with identity: %s", this.getClass().getName(), getIdentity());
    }


    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ClassUtils.getUserClass(obj))) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        return null == this.getIdentity() ? false : this.getIdentity().equals(that.getIdentity());
    }


    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getIdentity() ? 0 : getIdentity().hashCode() * 31;

        return hashCode;
    }
}

