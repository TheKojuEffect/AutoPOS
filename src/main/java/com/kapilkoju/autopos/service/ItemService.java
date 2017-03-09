package com.kapilkoju.autopos.service;

import com.kapilkoju.autopos.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Item.
 */
public interface ItemService {

    /**
     * Save a item.
     *
     * @param item the entity to save
     * @return the persisted entity
     */
    Item save(Item item);

    /**
     *  Get all the items.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Item> findAll(Pageable pageable);

    /**
     *  Get the "id" item.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Item findOne(Long id);

    /**
     *  Delete the "id" item.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
