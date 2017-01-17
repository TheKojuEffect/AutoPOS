package com.kapilkoju.autopos.catalog.service

import java.util.Optional

import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType
import org.springframework.data.jpa.repository.{EntityGraph, Query}
import org.springframework.data.repository.query.Param

trait ItemRepo extends AuditableBaseRepository[Item] {

  @Query("select item from Item item left join fetch item.tags where item.id =:id")
  def findOneWithEagerRelationships(@Param("id") id: Long): Item

  def findByCodeIgnoreCaseContainingOrNameIgnoreCaseContaining(codeQuery: String,
                                                               nameQuery: String,
                                                               pageable: Pageable
                                                              ): Page[Item]

  @EntityGraph(value = Item.Graph.detail, `type` = EntityGraphType.LOAD)
  def findById(id: Long): Optional[Item]
}
