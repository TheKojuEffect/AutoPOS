package io.koju.autopos.party.api

import io.koju.autopos.kernel.api.BaseApi
import io.koju.autopos.party.service.CustomerRepository
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/customers"))
class CustomerApi(customerRepository: CustomerRepository)
  extends BaseApi(customerRepository, "customer", "/api/customers") {

}
