package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.party.domain.Customer
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType

trait CustomerRepo extends AuditableRepository[Customer] {

  @EntityGraph(value = Customer.Graph.detail, `type` = EntityGraphType.LOAD)
  def getById(id: Long): Customer

}
