package com.kapilkoju.autopos.catalog.service

import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

trait ItemRepo extends AuditableBaseRepository[Item] {

  @Query("select item from Item item left join fetch item.tags where item.id =:id")
  def findOneWithEagerRelationships(@Param("id") id: Long): Item

  def findByCodeIgnoreCaseContainingOrNameIgnoreCaseContaining(codeQuery: String,
                                                               nameQuery: String,
                                                               pageable: Pageable
                                                              ): Page[Item]
}
