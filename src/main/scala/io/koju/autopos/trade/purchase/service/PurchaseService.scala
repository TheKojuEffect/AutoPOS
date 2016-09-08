package io.koju.autopos.trade.purchase.service

import io.koju.autopos.trade.purchase.domain.{Purchase, PurchaseLine}
import org.springframework.data.domain.{Page, Pageable}

trait PurchaseService {

  def getPurchases(pageable: Pageable): Page[Purchase]

  def createNewPurchase(): Purchase

  def updatePurchase(purchase: Purchase): Purchase

  def addPurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine

  def updatePurchaseLine(purchase: Purchase, purchaseLine: PurchaseLine): PurchaseLine

  def deletePurchaseLine(purchaseLine: PurchaseLine): Unit

}
