package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Customer

interface CustomerService {
    fun getCustomer(id: Long): Customer?
}

