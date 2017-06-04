package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vendor
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class VendorServiceImpl(private val vendorRepo: VendorRepo)
    extends VendorService {

    override def getVendor(id: Long): Option[Vendor] = Option(vendorRepo.getById(id))

    override def findVendors(query: String, pageable: Pageable): Page[Vendor] = {
        if (query.isEmpty)
            vendorRepo.findAll(pageable)
        else
            vendorRepo.findByNameIgnoreCaseContaining(query, pageable)
    }

}
