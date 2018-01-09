package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.party.domain.Vendor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph

interface VendorRepo : AuditableRepository<Vendor> {

    fun findByNameIgnoreCaseContaining(query: String, pageable: Pageable): Page<Vendor>

    @EntityGraph(value = Vendor.Graph.detail, type = EntityGraph.EntityGraphType.LOAD)
    fun getById(id: Long): Vendor?

}
