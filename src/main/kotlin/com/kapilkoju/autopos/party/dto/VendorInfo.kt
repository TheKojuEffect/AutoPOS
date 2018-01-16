package com.kapilkoju.autopos.party.dto

import com.kapilkoju.autopos.party.domain.Vendor

data class VendorInfo(val id: Long, val name: String) {
    constructor(vendor: Vendor) : this(vendor.getId()!!, vendor.name)
}