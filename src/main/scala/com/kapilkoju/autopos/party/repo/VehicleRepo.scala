package com.kapilkoju.autopos.party.repo

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.party.domain.Vehicle
import org.springframework.data.domain.{Page, Pageable}

trait VehicleRepo extends AuditableBaseRepository[Vehicle] {

  def findByNumberIgnoreCaseContainingOrOwnerNameIgnoreCaseContaining(numberQuery: String,
                                                                      ownerNameQuery: String,
                                                                      pageable: Pageable
                                                                     ): Page[Vehicle]

}
