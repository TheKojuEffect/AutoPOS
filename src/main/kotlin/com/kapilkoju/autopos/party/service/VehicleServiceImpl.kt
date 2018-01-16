package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vehicle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class VehicleServiceImpl(private val vehicleRepo: VehicleRepo) : VehicleService {

    override fun findVehicles(query: String, pageable: Pageable): Page<Vehicle> {
        return if (query.isEmpty())
            vehicleRepo.findAll(pageable)
        else
            vehicleRepo.findByNumberIgnoreCaseContainingOrOwnerNameIgnoreCaseContaining(query, query, pageable)
    }
}