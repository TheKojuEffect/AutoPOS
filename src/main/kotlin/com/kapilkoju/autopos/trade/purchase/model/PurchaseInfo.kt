package com.kapilkoju.autopos.trade.purchase.model

import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import java.time.Instant

data class PurchaseInfo(val id: Long, val date: Instant, val invoiceNumber: String?) {
    constructor(purchase: Purchase) : this(purchase.getId()!!, purchase.date, purchase.invoiceNumber)
}

