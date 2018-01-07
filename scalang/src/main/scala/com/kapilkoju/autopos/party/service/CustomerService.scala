package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Customer

trait CustomerService {
  def getCustomer(id: Long): Option[Customer]
}

