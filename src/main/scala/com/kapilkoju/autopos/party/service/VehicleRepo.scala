package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.party.domain.Vehicle
import org.springframework.data.domain.{Page, Pageable}

trait VehicleRepo extends AuditableRepository[Vehicle] {

  def findByNumberIgnoreCaseContainingOrOwnerNameIgnoreCaseContaining(numberQuery: String,
                                                                      ownerNameQuery: String,
                                                                      pageable: Pageable
                                                                     ): Page[Vehicle]

}
