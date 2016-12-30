package com.kapilkoju.autopos.party

import com.kapilkoju.autopos.party.domain.Customer
import com.kapilkoju.autopos.party.repo.CustomerRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

trait CustomerService {
  def getCustomer(id: Long): Option[Customer]
}


@Service
@Transactional
class CustomerServiceImpl(private val customerRepo: CustomerRepo)
  extends CustomerService {

  override def getCustomer(id: Long): Option[Customer] = Option(customerRepo.getById(id))

}
