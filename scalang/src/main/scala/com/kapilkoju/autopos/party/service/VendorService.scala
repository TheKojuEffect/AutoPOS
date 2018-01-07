package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vendor
import org.springframework.data.domain.{Page, Pageable}

trait VendorService {

    def getVendor(id: Long): Option[Vendor]

    def findVendors(query: String, pageable: Pageable): Page[Vendor]

}
