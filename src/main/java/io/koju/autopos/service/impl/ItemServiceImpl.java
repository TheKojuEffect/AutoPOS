package io.koju.autopos.service.impl;

import io.koju.autopos.service.ItemService;
import io.koju.autopos.domain.Item;
import io.koju.autopos.repository.ItemRepository;
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
 * Service Implementation for managing Item.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
    
    @Inject
    private ItemRepository itemRepository;
    
    /**
     * Save a item.
     * @return the persisted entity
     */
    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        Item result = itemRepository.save(item);
        return result;
    }

    /**
     *  get all the items.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Item> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        Page<Item> result = itemRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one item by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Item findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        Item item = itemRepository.findOneWithEagerRelationships(id);
        return item;
    }

    /**
     *  delete the  item by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.delete(id);
    }
}
