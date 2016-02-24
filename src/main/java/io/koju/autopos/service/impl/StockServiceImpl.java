package io.koju.autopos.service.impl;

import io.koju.autopos.service.StockService;
import io.koju.autopos.domain.Stock;
import io.koju.autopos.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Stock.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService{

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    
    @Inject
    private StockRepository stockRepository;
    
    /**
     * Save a stock.
     * @return the persisted entity
     */
    public Stock save(Stock stock) {
        log.debug("Request to save Stock : {}", stock);
        Stock result = stockRepository.save(stock);
        return result;
    }

    /**
     *  get all the stocks.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Stock> findAll(Pageable pageable) {
        log.debug("Request to get all Stocks");
        Page<Stock> result = stockRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one stock by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Stock findOne(Long id) {
        log.debug("Request to get Stock : {}", id);
        Stock stock = stockRepository.findOne(id);
        return stock;
    }

    /**
     *  delete the  stock by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Stock : {}", id);
        stockRepository.delete(id);
    }
}
