package com.kapilkoju.autopos.service;

import com.kapilkoju.autopos.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Sale.
 */
public interface SaleService {

    /**
     * Save a sale.
     *
     * @param sale the entity to save
     * @return the persisted entity
     */
    Sale save(Sale sale);

    /**
     *  Get all the sales.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Sale> findAll(Pageable pageable);

    /**
     *  Get the "id" sale.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Sale findOne(Long id);

    /**
     *  Delete the "id" sale.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
