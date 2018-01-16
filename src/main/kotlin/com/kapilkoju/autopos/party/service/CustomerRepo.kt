package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.party.domain.Customer
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType

interface CustomerRepo : AuditableRepository<Customer> {

  @EntityGraph(value = Customer.Graph.detail, type = EntityGraphType.LOAD)
  fun getById(id: Long): Customer?

}
