package io.koju.autopos.service;

import io.koju.autopos.domain.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Stock.
 */
public interface StockService {

    /**
     * Save a stock.
     * @return the persisted entity
     */
    public Stock save(Stock stock);

    /**
     *  get all the stocks.
     *  @return the list of entities
     */
    public Page<Stock> findAll(Pageable pageable);

    /**
     *  get the "id" stock.
     *  @return the entity
     */
    public Stock findOne(Long id);

    /**
     *  delete the "id" stock.
     */
    public void delete(Long id);
}
