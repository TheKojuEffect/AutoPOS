package com.kapilkoju.autopos.party.api

import com.kapilkoju.autopos.kernel.api.BaseApi
import com.kapilkoju.autopos.party.repo.CustomerRepo
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/customers"))
class CustomerApi(customerRepository: CustomerRepo)
  extends BaseApi(customerRepository, "customer", "/api/customers") {

}
