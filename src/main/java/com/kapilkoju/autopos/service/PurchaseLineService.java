package com.kapilkoju.autopos.service;

import com.kapilkoju.autopos.domain.PurchaseLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PurchaseLine.
 */
public interface PurchaseLineService {

    /**
     * Save a purchaseLine.
     *
     * @param purchaseLine the entity to save
     * @return the persisted entity
     */
    PurchaseLine save(PurchaseLine purchaseLine);

    /**
     *  Get all the purchaseLines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PurchaseLine> findAll(Pageable pageable);

    /**
     *  Get the "id" purchaseLine.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PurchaseLine findOne(Long id);

    /**
     *  Delete the "id" purchaseLine.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
