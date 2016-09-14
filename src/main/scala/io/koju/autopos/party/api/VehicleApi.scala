package io.koju.autopos.party.api

import java.util

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.kernel.api.CrudApi
import io.koju.autopos.party.domain.Vehicle
import io.koju.autopos.party.repo.VehicleRepo
import io.koju.autopos.party.service.VehicleService
import io.koju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/api/vehicles"))
class VehicleApi(vehicleRepository: VehicleRepo,
                 vehicleService: VehicleService)
  extends CrudApi(vehicleRepository, "vehicle", "/api/vehicles") {

  @GetMapping
  @Timed
  def getAll(pageable: Pageable,
             @RequestParam(value = "q", required = false, defaultValue = "")
             query: String): ResponseEntity[util.List[Vehicle]] = {

    val page = vehicleService.findVehicles(query, pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

}
