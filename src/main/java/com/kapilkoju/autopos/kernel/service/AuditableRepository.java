package com.kapilkoju.autopos.kernel.service;

import com.kapilkoju.autopos.kernel.domain.AuditableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditableRepository<T extends AuditableEntity>
        extends JpaRepository<T, Long> {
}
