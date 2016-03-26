package io.koju.autopos.repository;

import io.koju.autopos.domain.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Ledger entity.
 */
public interface LedgerRepository extends JpaRepository<Ledger,Long> {

}
