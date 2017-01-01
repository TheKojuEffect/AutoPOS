package com.kapilkoju.autopos.catalog.model

import com.kapilkoju.autopos.party.dto.VendorInfo
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import com.kapilkoju.autopos.trade.purchase.dto.PurchaseInfo


case class CostPriceInfo(price: BigDecimal, vendor: Option[VendorInfo], purchase: PurchaseInfo)

object CostPriceInfo {

  def apply(purchaseLine: PurchaseLine): CostPriceInfo = CostPriceInfo(
    purchaseLine.rate,
    Option(purchaseLine.purchase.vendor).map(v => VendorInfo(v)),
    PurchaseInfo(purchaseLine.purchase)
  )

}