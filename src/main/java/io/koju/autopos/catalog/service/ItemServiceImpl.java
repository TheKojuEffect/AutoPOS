package io.koju.autopos.catalog.service;

import com.mysema.query.types.Predicate;
import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.domain.QItem;
import io.koju.autopos.catalog.schema.ItemFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class ItemServiceImpl implements ItemService {

    private final static Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;

    @Autowired
    ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        Item result = itemRepository.save(item);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Item> findAll(ItemFilter itemFilter, Pageable pageable) {
        log.debug("Request to get all Items");

        Predicate itemFilterPredicate = itemFilter.getNameFilter().map(nameFilter -> {
            QItem item = QItem.item;
            String filterTerm = nameFilter.getFilterTerm();
            switch (nameFilter.getMatchType()) {
                case EXACT:
                    return item.name.equalsIgnoreCase(filterTerm);
                case START:
                    return item.name.startsWithIgnoreCase(filterTerm);
                case END:
                    return item.name.endsWithIgnoreCase(filterTerm);
                case ANYWHERE:
                    return item.name.containsIgnoreCase(filterTerm);
            }
            return null;
        }).orElse(null);

        return itemRepository.findAll(itemFilterPredicate, pageable);
    }

    @Transactional(readOnly = true)
    public Item findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        Item item = itemRepository.findOneWithEagerRelationships(id);
        return item;
    }

    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.delete(id);
    }
}
