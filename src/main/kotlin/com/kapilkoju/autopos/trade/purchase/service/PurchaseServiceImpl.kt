package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.catalog.service.ItemService
import com.kapilkoju.autopos.stockbook.service.StockBookService
import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import com.kapilkoju.autopos.trade.purchase.dto.CreatePurchaseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant


@Service
@Transactional
class PurchaseServiceImpl(private val purchaseRepo: PurchaseRepo,
                          private val purchaseLineRepo: PurchaseLineRepo,
                          private val itemService: ItemService,
                          private val stockBookService: StockBookService)
    : PurchaseService {

    override fun getPurchases(vat: Boolean, pageable: Pageable): Page<Purchase> =
            purchaseRepo.findAllByVat(vat, pageable)

    override fun createNewPurchase(createPurchaseDto: CreatePurchaseDto): Purchase {
        val purchase = Purchase()
        purchase.vat = createPurchaseDto.vat
        purchase.date = Instant.now()
        return purchaseRepo.save(purchase)
    }

    override fun updatePurchase(purchase: Purchase): Purchase =
            purchaseRepo.save(purchase)


    override fun addPurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine {
        purchaseLine.purchase = purchase
        purchaseLine.setId(null)
        purchaseLineRepo.save(purchaseLine)

        if (purchaseLine.purchase.vat) {
            stockBookService.addStock(purchaseLine.item, purchaseLine.quantity, purchaseLine.rate)
        } else {
            itemService.addQuantity(purchaseLine.item, purchaseLine.quantity)
        }
        return purchaseLine
    }

    override fun updatePurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine {
        purchaseLine.purchase = purchase

        val dbPurchaseLine = purchaseLineRepo.findOne(purchaseLine.getId())
        val quantityChanged = purchaseLine.quantity - dbPurchaseLine.quantity

        purchaseLineRepo.save(purchaseLine)
        if (purchase.vat) {
            stockBookService.adjustStock(purchaseLine.item, quantityChanged, purchaseLine.rate)
        } else {
            itemService.adjustQuantity(purchaseLine.item, quantityChanged)
        }
        return purchaseLine
    }

    override fun deletePurchaseLine(purchaseLine: PurchaseLine) {
        purchaseLineRepo.delete(purchaseLine)

        if (purchaseLine.purchase.vat) {
            stockBookService.subtractStock(purchaseLine.item, purchaseLine.quantity) // Need to update the cost price to latest
        } else {
            itemService.subtractQuantity(purchaseLine.item, purchaseLine.quantity)
        }
    }

    override fun getPurchaseLines(itemId: Long): List<PurchaseLine> =
            purchaseLineRepo.findByItemIdOrderByPurchaseDateDesc(itemId)


    override fun deletePurchase(purchase: Purchase) =
            purchaseRepo.delete(purchase)

}
