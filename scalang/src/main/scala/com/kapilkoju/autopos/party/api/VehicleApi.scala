package com.kapilkoju.autopos.party.api

import java.util

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.kernel.api.CrudApi
import com.kapilkoju.autopos.party.domain.Vehicle
import com.kapilkoju.autopos.party.service.{VehicleRepo, VehicleService}
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
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
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehicles")
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

}
