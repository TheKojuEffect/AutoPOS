package io.koju.autopos.shared;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity extends AbstractPersistable<Long> {
}
