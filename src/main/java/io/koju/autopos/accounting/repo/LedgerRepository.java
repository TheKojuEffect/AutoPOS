package io.koju.autopos.accounting.repo;

import io.koju.autopos.accounting.domain.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Ledger entity.
 */
public interface LedgerRepository extends JpaRepository<Ledger,Long> {

}
