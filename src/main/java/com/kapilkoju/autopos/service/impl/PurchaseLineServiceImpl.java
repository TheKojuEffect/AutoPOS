package com.kapilkoju.autopos.service.impl;

import com.kapilkoju.autopos.service.PurchaseLineService;
import com.kapilkoju.autopos.domain.PurchaseLine;
import com.kapilkoju.autopos.repository.PurchaseLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing PurchaseLine.
 */
@Service
@Transactional
public class PurchaseLineServiceImpl implements PurchaseLineService{

    private final Logger log = LoggerFactory.getLogger(PurchaseLineServiceImpl.class);
    
    private final PurchaseLineRepository purchaseLineRepository;

    public PurchaseLineServiceImpl(PurchaseLineRepository purchaseLineRepository) {
        this.purchaseLineRepository = purchaseLineRepository;
    }

    /**
     * Save a purchaseLine.
     *
     * @param purchaseLine the entity to save
     * @return the persisted entity
     */
    @Override
    public PurchaseLine save(PurchaseLine purchaseLine) {
        log.debug("Request to save PurchaseLine : {}", purchaseLine);
        PurchaseLine result = purchaseLineRepository.save(purchaseLine);
        return result;
    }

    /**
     *  Get all the purchaseLines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseLine> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseLines");
        Page<PurchaseLine> result = purchaseLineRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one purchaseLine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PurchaseLine findOne(Long id) {
        log.debug("Request to get PurchaseLine : {}", id);
        PurchaseLine purchaseLine = purchaseLineRepository.findOne(id);
        return purchaseLine;
    }

    /**
     *  Delete the  purchaseLine by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseLine : {}", id);
        purchaseLineRepository.delete(id);
    }
}
