package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.party.domain.Vehicle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface VehicleRepo : AuditableRepository<Vehicle> {

    fun findByNumberIgnoreCaseContainingOrOwnerNameIgnoreCaseContaining(numberQuery: String,
                                                                        ownerNameQuery: String,
                                                                        pageable: Pageable
    ): Page<Vehicle>

}
