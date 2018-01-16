package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vehicle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface VehicleService {
    fun findVehicles(query: String, pageable: Pageable): Page<Vehicle>
}
