package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import com.kapilkoju.autopos.trade.purchase.dto.CreatePurchaseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PurchaseService {

    fun getPurchases(vat: Boolean, pageable: Pageable): Page<Purchase>

    fun createNewPurchase(createPurchaseDto: CreatePurchaseDto): Purchase

    fun updatePurchase(purchase: Purchase): Purchase

    fun deletePurchase(purchase: Purchase)

    fun addPurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine

    fun updatePurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine

    fun deletePurchaseLine(purchaseLine: PurchaseLine)

    fun getPurchaseLines(itemId: Long): List<PurchaseLine>

}
