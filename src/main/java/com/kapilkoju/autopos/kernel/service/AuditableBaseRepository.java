package com.kapilkoju.autopos.kernel.service;

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditableBaseRepository<T extends AuditableBaseEntity>
        extends JpaRepository<T, Long> {
}
