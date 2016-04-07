package io.koju.autopos.party.service;

import io.koju.autopos.party.domain.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LedgerEntry entity.
 */
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {

}
