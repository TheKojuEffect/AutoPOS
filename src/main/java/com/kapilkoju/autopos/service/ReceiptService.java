package com.kapilkoju.autopos.service;

import com.kapilkoju.autopos.domain.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Receipt.
 */
public interface ReceiptService {

    /**
     * Save a receipt.
     *
     * @param receipt the entity to save
     * @return the persisted entity
     */
    Receipt save(Receipt receipt);

    /**
     *  Get all the receipts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Receipt> findAll(Pageable pageable);

    /**
     *  Get the "id" receipt.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Receipt findOne(Long id);

    /**
     *  Delete the "id" receipt.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
