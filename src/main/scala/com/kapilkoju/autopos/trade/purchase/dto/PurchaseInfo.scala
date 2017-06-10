package com.kapilkoju.autopos.trade.purchase.dto

import java.time.LocalDateTime

import com.kapilkoju.autopos.trade.purchase.domain.Purchase

case class PurchaseInfo(id: Long, date: LocalDateTime, invoiceNumber: String)

object PurchaseInfo {

  def apply(purchase: Purchase): PurchaseInfo = {
    require(purchase != null)
    PurchaseInfo(purchase.getId, purchase.date, purchase.invoiceNumber)
  }

}
