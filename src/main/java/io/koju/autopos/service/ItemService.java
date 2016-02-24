package io.koju.autopos.service;

import io.koju.autopos.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Item.
 */
public interface ItemService {

    /**
     * Save a item.
     * @return the persisted entity
     */
    public Item save(Item item);

    /**
     *  get all the items.
     *  @return the list of entities
     */
    public Page<Item> findAll(Pageable pageable);

    /**
     *  get the "id" item.
     *  @return the entity
     */
    public Item findOne(Long id);

    /**
     *  delete the "id" item.
     */
    public void delete(Long id);
}
