package io.koju.autopos.shared.domain;

import org.springframework.util.ClassUtils;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractEntity implements Persistable<Long> {

    private static final long serialVersionUID = -5554308939380869754L;

    protected abstract void setId(final Long id);

    @Transient
    public boolean isNew() {
        return null == getId();
    }


    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
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

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }


    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}

