package com.kapilkoju.autopos.service.impl;

import com.kapilkoju.autopos.service.PriceHistoryService;
import com.kapilkoju.autopos.domain.PriceHistory;
import com.kapilkoju.autopos.repository.PriceHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing PriceHistory.
 */
@Service
@Transactional
public class PriceHistoryServiceImpl implements PriceHistoryService{

    private final Logger log = LoggerFactory.getLogger(PriceHistoryServiceImpl.class);
    
    private final PriceHistoryRepository priceHistoryRepository;

    public PriceHistoryServiceImpl(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    /**
     * Save a priceHistory.
     *
     * @param priceHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public PriceHistory save(PriceHistory priceHistory) {
        log.debug("Request to save PriceHistory : {}", priceHistory);
        PriceHistory result = priceHistoryRepository.save(priceHistory);
        return result;
    }

    /**
     *  Get all the priceHistories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PriceHistory> findAll(Pageable pageable) {
        log.debug("Request to get all PriceHistories");
        Page<PriceHistory> result = priceHistoryRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one priceHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PriceHistory findOne(Long id) {
        log.debug("Request to get PriceHistory : {}", id);
        PriceHistory priceHistory = priceHistoryRepository.findOne(id);
        return priceHistory;
    }

    /**
     *  Delete the  priceHistory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceHistory : {}", id);
        priceHistoryRepository.delete(id);
    }
}
