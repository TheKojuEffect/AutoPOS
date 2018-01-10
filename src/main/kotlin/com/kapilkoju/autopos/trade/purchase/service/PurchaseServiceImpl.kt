package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.catalog.service.ItemService
import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant


@Service
@Transactional
class PurchaseServiceImpl(private val purchaseRepo: PurchaseRepo,
                          private val purchaseLineRepo: PurchaseLineRepo,
                          private val itemService: ItemService)
    : PurchaseService {

    override fun getPurchases(pageable: Pageable): Page<Purchase> =
            purchaseRepo.findAll(pageable)

    override fun createNewPurchase(): Purchase {
        val purchase = Purchase()
        purchase.date = Instant.now()
        return purchaseRepo.save(purchase)
    }

    override fun updatePurchase(purchase: Purchase): Purchase =
            purchaseRepo.save(purchase)


    override fun addPurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine {
        purchaseLine.purchase = purchase
        purchaseLine.setId(null)
        purchaseLineRepo.save(purchaseLine)
        itemService.addQuantity(purchaseLine.item, purchaseLine.quantity)
        return purchaseLine
    }

    override fun updatePurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine {
        purchaseLine.purchase = purchase

        val dbPurchaseLine = purchaseLineRepo.findOne(purchaseLine.getId())
        val quantityChanged = purchaseLine.quantity - dbPurchaseLine.quantity

        purchaseLineRepo.save(purchaseLine)
        itemService.adjustQuantity(purchaseLine.item, quantityChanged)
        return purchaseLine
    }

    override fun deletePurchaseLine(purchaseLine: PurchaseLine) {
        purchaseLineRepo.delete(purchaseLine)
        itemService.addQuantity(purchaseLine.item, purchaseLine.quantity)
    }

    override fun getPurchaseLines(itemId: Long): List<PurchaseLine> =
            purchaseLineRepo.findByItemIdOrderByPurchaseDateDesc(itemId)


    override fun deletePurchase(purchase: Purchase) =
            purchaseRepo.delete(purchase)

}
