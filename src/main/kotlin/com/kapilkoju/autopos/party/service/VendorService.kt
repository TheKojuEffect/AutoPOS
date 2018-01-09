package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vendor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface VendorService {

    fun getVendor(id: Long): Vendor?

    fun findVendors(query: String, pageable: Pageable): Page<Vendor>

}
