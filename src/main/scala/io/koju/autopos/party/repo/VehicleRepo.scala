package io.koju.autopos.party.repo

import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.party.domain.Vehicle
import org.springframework.data.domain.{Page, Pageable}

trait VehicleRepo extends AuditableBaseRepository[Vehicle] {

  def findByNumberIgnoreCaseContainingOrOwnerNameIgnoreCaseContaining(numberQuery: String,
                                                                      ownerNameQuery: String,
                                                                      pageable: Pageable
                                                                     ): Page[Vehicle]

}
