package io.koju.autopos.service;

import io.koju.autopos.domain.Ledger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Ledger.
 */
public interface LedgerService {

    /**
     * Save a ledger.
     * @return the persisted entity
     */
    public Ledger save(Ledger ledger);

    /**
     *  get all the ledgers.
     *  @return the list of entities
     */
    public Page<Ledger> findAll(Pageable pageable);

    /**
     *  get the "id" ledger.
     *  @return the entity
     */
    public Ledger findOne(Long id);

    /**
     *  delete the "id" ledger.
     */
    public void delete(Long id);
}
