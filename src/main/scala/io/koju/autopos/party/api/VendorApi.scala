package io.koju.autopos.party.api

import io.koju.autopos.kernel.api.BaseApi
import io.koju.autopos.party.repo.VendorRepo
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/vendors"))
class VendorApi(vendorRepository: VendorRepo)
  extends BaseApi(vendorRepository, "vendor", "/api/vendors") {

}
