package io.koju.autopos.party.api

import io.koju.autopos.party.service.VehicleRepository
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/vehicles"))
class VehicleApi(vehicleRepository: VehicleRepository)
  extends BaseApi(vehicleRepository, "vehicle", "/api/vehicles") {

}
