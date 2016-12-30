package com.kapilkoju.autopos.party.repo

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.party.domain.Customer
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType

trait CustomerRepo extends AuditableBaseRepository[Customer] {

  @EntityGraph(value = Customer.Graph.detail, `type` = EntityGraphType.LOAD)
  def getById(id: Long): Customer

}
