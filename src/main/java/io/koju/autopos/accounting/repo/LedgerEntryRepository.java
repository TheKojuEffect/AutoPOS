package io.koju.autopos.accounting.repo;

import io.koju.autopos.accounting.domain.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LedgerEntry entity.
 */
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {

}
