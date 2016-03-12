package io.koju.autopos.catalog.service;

import com.mysema.query.types.Predicate;
import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.domain.QItem;
import io.koju.autopos.catalog.domain.QuantityInfo;
import io.koju.autopos.catalog.struct.filter.ItemFilter;
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

    private final QuantityInfoRepository quantityInfoRepository;

    @Autowired
    ItemServiceImpl(ItemRepository itemRepository, QuantityInfoRepository quantityInfoRepository) {
        this.itemRepository = itemRepository;
        this.quantityInfoRepository = quantityInfoRepository;
    }

    @Override
    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        QuantityInfo quantityInfo = new QuantityInfo();
        quantityInfoRepository.save(quantityInfo);
        item.setQuantityInfo(quantityInfo);
        Item result = itemRepository.save(item);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> findAll(ItemFilter itemFilter, Pageable pageable) {
        log.debug("Request to get all Items");
        final Predicate filterPredicate = itemFilter.toQueryDslPredicate(QItem.item);
        return itemRepository.findAll(filterPredicate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Item findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        Item item = itemRepository.findOneWithEagerRelationships(id);
        return item;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.delete(id);
    }
}
