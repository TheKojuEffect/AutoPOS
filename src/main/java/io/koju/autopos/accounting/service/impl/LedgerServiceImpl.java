package io.koju.autopos.accounting.service.impl;

import io.koju.autopos.accounting.service.LedgerService;
import io.koju.autopos.accounting.domain.Ledger;
import io.koju.autopos.accounting.repo.LedgerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Service Implementation for managing Ledger.
 */
@Service
@Transactional
public class LedgerServiceImpl implements LedgerService{

    private final Logger log = LoggerFactory.getLogger(LedgerServiceImpl.class);
    
    @Inject
    private LedgerRepository ledgerRepository;
    
    /**
     * Save a ledger.
     * @return the persisted entity
     */
    public Ledger save(Ledger ledger) {
        log.debug("Request to save Ledger : {}", ledger);
        Ledger result = ledgerRepository.save(ledger);
        return result;
    }

    /**
     *  get all the ledgers.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Ledger> findAll(Pageable pageable) {
        log.debug("Request to get all Ledgers");
        Page<Ledger> result = ledgerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one ledger by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Ledger findOne(Long id) {
        log.debug("Request to get Ledger : {}", id);
        Ledger ledger = ledgerRepository.findOne(id);
        return ledger;
    }

    /**
     *  delete the  ledger by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ledger : {}", id);
        ledgerRepository.delete(id);
    }
}
