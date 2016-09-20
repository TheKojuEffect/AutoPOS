package io.koju.autopos.kernel.service;

import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditableBaseRepository<T extends AuditableBaseEntity>
        extends JpaRepository<T, Long> {
}
