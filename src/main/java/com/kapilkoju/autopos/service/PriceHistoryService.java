package com.kapilkoju.autopos.service;

import com.kapilkoju.autopos.domain.PriceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PriceHistory.
 */
public interface PriceHistoryService {

    /**
     * Save a priceHistory.
     *
     * @param priceHistory the entity to save
     * @return the persisted entity
     */
    PriceHistory save(PriceHistory priceHistory);

    /**
     *  Get all the priceHistories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PriceHistory> findAll(Pageable pageable);

    /**
     *  Get the "id" priceHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PriceHistory findOne(Long id);

    /**
     *  Delete the "id" priceHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
