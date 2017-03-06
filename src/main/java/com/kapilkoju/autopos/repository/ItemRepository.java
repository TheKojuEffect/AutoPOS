package com.kapilkoju.autopos.repository;

import com.kapilkoju.autopos.domain.Item;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Item entity.
 */
@SuppressWarnings("unused")
public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query("select distinct item from Item item left join fetch item.tags")
    List<Item> findAllWithEagerRelationships();

    @Query("select item from Item item left join fetch item.tags where item.id =:id")
    Item findOneWithEagerRelationships(@Param("id") Long id);

}
