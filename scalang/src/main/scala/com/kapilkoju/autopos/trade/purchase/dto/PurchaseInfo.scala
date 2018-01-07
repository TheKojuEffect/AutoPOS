package com.kapilkoju.autopos.trade.purchase.dto

import java.time.Instant

import com.kapilkoju.autopos.trade.purchase.domain.Purchase

case class PurchaseInfo(id: Long, date: Instant, invoiceNumber: String)

object PurchaseInfo {

  def apply(purchase: Purchase): PurchaseInfo = {
    require(purchase != null)
    PurchaseInfo(purchase.getId, purchase.date, purchase.invoiceNumber)
  }

}
