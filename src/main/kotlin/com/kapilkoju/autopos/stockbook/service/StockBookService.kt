package com.kapilkoju.autopos.stockbook.service

import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine

interface StockBookService {

    fun adjustQuantity(purchaseLine: PurchaseLine, number: Int)

    fun addQuantity(purchaseLine: PurchaseLine, number: Int) {
        adjustQuantity(purchaseLine, number)
    }

    fun subtractQuantity(purchaseLine: PurchaseLine, number: Int) {
        adjustQuantity(purchaseLine, -number)
    }
}
