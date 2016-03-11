package io.koju.autopos.catalog.service;

import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.struct.filter.ItemFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    Item save(Item item);

    Page<Item> findAll(ItemFilter itemFilter, Pageable pageable);

    Item findOne(Long id);

    void delete(Long id);
}
