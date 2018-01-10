package com.kapilkoju.autopos.catalog.model

import com.kapilkoju.autopos.party.dto.VendorInfo
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import com.kapilkoju.autopos.trade.purchase.model.PurchaseInfo
import java.math.BigDecimal


data class CostPriceInfo(val price: BigDecimal, val vendor: VendorInfo?, val purchase: PurchaseInfo) {
    constructor(purchaseLine: PurchaseLine) : this(
            purchaseLine.rate,
            if (purchaseLine.purchase.vendor != null) VendorInfo(purchaseLine.purchase.vendor) else null,
            PurchaseInfo(purchaseLine.purchase)
    )
}
