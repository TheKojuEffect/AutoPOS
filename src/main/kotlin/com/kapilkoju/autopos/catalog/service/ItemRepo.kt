package com.kapilkoju.autopos.catalog.service

import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.kernel.service.AuditableRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph

interface ItemRepo : AuditableRepository<Item> {

    fun findByCodeIgnoreCaseContainingOrNameIgnoreCaseContaining(codeQuery: String,
                                                                 nameQuery: String,
                                                                 pageable: Pageable
    ): Page<Item>

    @EntityGraph(value = "Item.detail", `type` = EntityGraph.EntityGraphType.LOAD)
    fun findById(id: Long): Item?
}
