package io.koju.autopos.party.api

import io.koju.autopos.kernel.api.BaseApi
import io.koju.autopos.party.service.VendorRepository
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/vendors"))
class VendorApi(vendorRepository: VendorRepository)
  extends BaseApi(vendorRepository, "vendor", "/api/vendors") {

}
