package com.kapilkoju.autopos.service;

import com.kapilkoju.autopos.domain.SaleLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing SaleLine.
 */
public interface SaleLineService {

    /**
     * Save a saleLine.
     *
     * @param saleLine the entity to save
     * @return the persisted entity
     */
    SaleLine save(SaleLine saleLine);

    /**
     *  Get all the saleLines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SaleLine> findAll(Pageable pageable);

    /**
     *  Get the "id" saleLine.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SaleLine findOne(Long id);

    /**
     *  Delete the "id" saleLine.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
