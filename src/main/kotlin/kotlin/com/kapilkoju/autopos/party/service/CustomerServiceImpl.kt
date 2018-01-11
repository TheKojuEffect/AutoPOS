package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Customer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class CustomerServiceImpl(private val customerRepo: CustomerRepo) : CustomerService {

    override fun getCustomer(id: Long): Customer? = customerRepo.getById(id)

}
