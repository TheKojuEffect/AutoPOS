package io.koju.autopos.shared.service.repo;

import io.koju.autopos.shared.domain.AuditableBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface AuditableBaseRepository<T extends AuditableBaseEntity>
    extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {
}
