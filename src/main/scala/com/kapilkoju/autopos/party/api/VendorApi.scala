package com.kapilkoju.autopos.party.api

import com.kapilkoju.autopos.kernel.api.BaseApi
import com.kapilkoju.autopos.party.repo.VendorRepo
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/vendors"))
class VendorApi(vendorRepository: VendorRepo)
  extends BaseApi(vendorRepository, "vendor", "/api/vendors") {

}
