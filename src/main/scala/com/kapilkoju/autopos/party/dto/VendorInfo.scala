package com.kapilkoju.autopos.party.dto

import com.kapilkoju.autopos.party.domain.Vendor

case class VendorInfo(id: Long, name: String)

object VendorInfo {

  def apply(vendor: Vendor): VendorInfo = {
    require(vendor != null)

    VendorInfo(vendor.id, vendor.name)
  }
}
