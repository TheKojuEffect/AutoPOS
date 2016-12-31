package com.kapilkoju.autopos.party.service

import com.kapilkoju.autopos.party.domain.Vendor

trait VendorService {
  def getVendor(id: Long): Option[Vendor]
}
