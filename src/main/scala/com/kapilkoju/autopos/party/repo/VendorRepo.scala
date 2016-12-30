package com.kapilkoju.autopos.party.repo

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.party.domain.Vendor
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType

trait VendorRepo extends AuditableBaseRepository[Vendor] {

  @EntityGraph(value = Vendor.Graph.detail, `type` = EntityGraphType.LOAD)
  def getById(id: Long): Vendor

}
