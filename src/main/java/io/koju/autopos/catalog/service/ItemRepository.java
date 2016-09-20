package io.koju.autopos.catalog.service;

import io.koju.autopos.catalog.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository
        extends JpaRepository<Item, Long>, QueryDslPredicateExecutor<Item> {

    @Query("select distinct item from Item item left join fetch item.tags")
    List<Item> findAllWithEagerRelationships();

    @Query("select item from Item item left join fetch item.tags where item.id =:id")
    Item findOneWithEagerRelationships(@Param("id") Long id);

    Page<Item> findByCodeIgnoreCaseContainingOrNameIgnoreCaseContaining(String codeQuery, String nameQuery, Pageable pageable);

}
