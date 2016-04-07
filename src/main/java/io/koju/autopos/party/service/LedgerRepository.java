package io.koju.autopos.party.service;

import io.koju.autopos.party.domain.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Ledger entity.
 */
public interface LedgerRepository extends JpaRepository<Ledger,Long> {

}
