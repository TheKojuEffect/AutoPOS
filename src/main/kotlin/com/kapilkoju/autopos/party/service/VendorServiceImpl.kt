package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vendor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VendorServiceImpl(private val vendorRepo: VendorRepo) : VendorService {

    override fun getVendor(id: Long): Vendor? = vendorRepo.getById(id)

    override fun findVendors(query: String, pageable: Pageable): Page<Vendor> {
        return if (query.isEmpty())
            vendorRepo.findAll(pageable)
        else
            vendorRepo.findByNameIgnoreCaseContaining(query, pageable)
    }

}
