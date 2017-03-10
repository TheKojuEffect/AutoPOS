package com.kapilkoju.autopos.service;

import com.kapilkoju.autopos.domain.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Purchase.
 */
public interface PurchaseService {

    /**
     * Save a purchase.
     *
     * @param purchase the entity to save
     * @return the persisted entity
     */
    Purchase save(Purchase purchase);

    /**
     *  Get all the purchases.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Purchase> findAll(Pageable pageable);

    /**
     *  Get the "id" purchase.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Purchase findOne(Long id);

    /**
     *  Delete the "id" purchase.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
