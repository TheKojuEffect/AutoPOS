package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.trade.purchase.domain.{Purchase, PurchaseLine}
import org.springframework.data.domain.{Page, Pageable}

trait PurchaseService {

  def getPurchases(pageable: Pageable): Page[Purchase]

  def createNewPurchase(): Purchase

  def updatePurchase(purchase: Purchase): Purchase

  def deletePurchase(purchase: Purchase): Unit

  def addPurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine

  def updatePurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine

  def deletePurchaseLine(purchaseLine: PurchaseLine): Unit

  def getPurchaseLines(itemId: Long): List[PurchaseLine]

}
