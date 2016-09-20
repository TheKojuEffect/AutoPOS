package io.koju.autopos.catalog.service;

import io.koju.autopos.catalog.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    Item save(Item item);

    Page<Item> findAll(String queryTerm, Pageable pageable);

    Item findOne(Long id);

    void delete(Long id);

    void adjustQuantity(Item item, Integer number);

    default void addQuantity(Item item, Integer number) {
        adjustQuantity(item, number);
    }

    default void subtractQuantity(Item item, Integer number) {
        adjustQuantity(item, -number);
    }

}
