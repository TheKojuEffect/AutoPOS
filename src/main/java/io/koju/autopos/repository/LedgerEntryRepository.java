package io.koju.autopos.repository;

import io.koju.autopos.domain.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LedgerEntry entity.
 */
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {

}
