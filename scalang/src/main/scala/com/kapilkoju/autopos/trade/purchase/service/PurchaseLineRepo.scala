package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

trait PurchaseLineRepo extends AuditableRepository[PurchaseLine] {

  @Query("SELECT pl FROM PurchaseLine pl where pl.item.id = :itemId order by pl.purchase.date DESC")
  def findByItemIdOrderByPurchaseDateDesc(@Param("itemId") itemId: java.lang.Long): java.util.List[PurchaseLine]

}

