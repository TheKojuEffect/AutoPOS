package io.koju.autopos.trade.purchase.service

import java.time.LocalDateTime

import io.koju.autopos.catalog.service.ItemService
import io.koju.autopos.trade.purchase.domain.{Purchase, PurchaseLine, QPurchase}
import io.koju.autopos.trade.purchase.repo.{PurchaseLineRepo, PurchaseRepo}
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PurchaseServiceImpl(private val purchaseRepo: PurchaseRepo,
                          private val purchaseLineRepo: PurchaseLineRepo,
                          private val itemService: ItemService)
  extends PurchaseService {

  override def getPurchases(pageable: Pageable) =
    purchaseRepo.findAll(pageable)

  override def createNewPurchase() = {
    val purchase = new Purchase
    purchase.setDate(LocalDateTime.now())
    purchaseRepo.save(purchase)
  }

  override def updatePurchase(purchase: Purchase) = {
    purchaseRepo.save(purchase)
  }

  override def addPurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine = {
    purchaseLine.setPurchase(purchase)
    purchaseLine.setId(null)
    purchaseLineRepo.save(purchaseLine)
    itemService.substractQuantity(purchaseLine.getItem, purchaseLine.getQuantity)
    purchaseLine
  }

  override def updatePurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine = {
    purchaseLine.setPurchase(purchase)

    val dbPurchaseLine = purchaseLineRepo.findOne(purchaseLine.getId)
    val quantityChanged = purchaseLine.getQuantity - dbPurchaseLine.getQuantity

    purchaseLineRepo.save(purchaseLine)
    itemService.adjustQuantity(purchaseLine.getItem, -quantityChanged)
    purchaseLine
  }

  override def deletePurchaseLine(purchaseLine: PurchaseLine): Unit = {
    purchaseLineRepo.delete(purchaseLine)
    itemService.addQuantity(purchaseLine.getItem, purchaseLine.getQuantity)
  }
}
