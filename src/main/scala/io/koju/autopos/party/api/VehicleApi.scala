package io.koju.autopos.party.api

import java.util

import com.codahale.metrics.annotation.Timed
import com.querydsl.core.types.Predicate
import io.koju.autopos.kernel.api.CrudApi
import io.koju.autopos.party.domain.Vehicle
import io.koju.autopos.party.service.VehicleRepository
import io.koju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.binding.QuerydslPredicate
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/vehicles"))
class VehicleApi(vehicleRepository: VehicleRepository)
  extends CrudApi(vehicleRepository, "vehicle", "/api/vehicles") {

  @GetMapping
  @Timed
  def getAll(@QuerydslPredicate(root = classOf[Vehicle]) predicate: Predicate,
             pageable: Pageable): ResponseEntity[util.List[Vehicle]] = {

    val page = repo.findAll(predicate, pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

}
