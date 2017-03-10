package com.kapilkoju.autopos.service.impl;

import com.kapilkoju.autopos.service.SaleLineService;
import com.kapilkoju.autopos.domain.SaleLine;
import com.kapilkoju.autopos.repository.SaleLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing SaleLine.
 */
@Service
@Transactional
public class SaleLineServiceImpl implements SaleLineService{

    private final Logger log = LoggerFactory.getLogger(SaleLineServiceImpl.class);
    
    private final SaleLineRepository saleLineRepository;

    public SaleLineServiceImpl(SaleLineRepository saleLineRepository) {
        this.saleLineRepository = saleLineRepository;
    }

    /**
     * Save a saleLine.
     *
     * @param saleLine the entity to save
     * @return the persisted entity
     */
    @Override
    public SaleLine save(SaleLine saleLine) {
        log.debug("Request to save SaleLine : {}", saleLine);
        SaleLine result = saleLineRepository.save(saleLine);
        return result;
    }

    /**
     *  Get all the saleLines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SaleLine> findAll(Pageable pageable) {
        log.debug("Request to get all SaleLines");
        Page<SaleLine> result = saleLineRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one saleLine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SaleLine findOne(Long id) {
        log.debug("Request to get SaleLine : {}", id);
        SaleLine saleLine = saleLineRepository.findOne(id);
        return saleLine;
    }

    /**
     *  Delete the  saleLine by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SaleLine : {}", id);
        saleLineRepository.delete(id);
    }
}
