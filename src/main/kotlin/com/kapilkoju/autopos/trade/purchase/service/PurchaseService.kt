package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PurchaseService {

    fun getPurchases(pageable: Pageable): Page<Purchase>

    fun createNewPurchase(): Purchase

    fun updatePurchase(purchase: Purchase): Purchase

    fun deletePurchase(purchase: Purchase)

    fun addPurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine

    fun updatePurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine

    fun deletePurchaseLine(purchaseLine: PurchaseLine)

    fun getPurchaseLines(itemId: Long): List<PurchaseLine>

}
