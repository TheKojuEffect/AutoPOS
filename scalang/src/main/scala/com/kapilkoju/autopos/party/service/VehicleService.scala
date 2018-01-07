package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vehicle
import org.springframework.data.domain.{Page, Pageable}

trait VehicleService {
  def findVehicles(query: String, pageable: Pageable): Page[Vehicle]
}
