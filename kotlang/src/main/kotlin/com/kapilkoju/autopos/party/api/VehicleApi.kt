package com.kapilkoju.autopos.party.api

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.kernel.api.CrudApi
import com.kapilkoju.autopos.party.domain.Vehicle
import com.kapilkoju.autopos.party.service.VehicleRepo
import com.kapilkoju.autopos.party.service.VehicleService
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [VehicleApi.baseUrl])
class VehicleApi(vehicleRepository: VehicleRepo,
                 private val vehicleService: VehicleService)
    : CrudApi<Vehicle>(vehicleRepository, "vehicle", baseUrl) {

    @GetMapping
    @Timed
    fun getAll(pageable: Pageable, @RequestParam(value = "q", required = false, defaultValue = "") query: String): ResponseEntity<List<Vehicle>> {
        val page = vehicleService.findVehicles(query, pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    companion object {
        const val baseUrl = "/api/vehicles"
    }

}
