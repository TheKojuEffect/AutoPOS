package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vehicle
import com.kapilkoju.autopos.party.repo.VehicleRepo
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service

trait VehicleService {
  def findVehicles(query: String, pageable: Pageable): Page[Vehicle]
}

@Service
class VehicleServiceImpl(private val vehicleRepo: VehicleRepo)
  extends VehicleService {

  override def findVehicles(query: String, pageable: Pageable) = {
    if (query.isEmpty)
      vehicleRepo.findAll(pageable)
    else
      vehicleRepo.findByNumberIgnoreCaseContainingOrOwnerNameIgnoreCaseContaining(query, query, pageable)
  }
}
