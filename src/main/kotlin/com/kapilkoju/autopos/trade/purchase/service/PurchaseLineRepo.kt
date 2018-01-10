package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PurchaseLineRepo : AuditableRepository<PurchaseLine> {

    @Query("SELECT pl FROM PurchaseLine pl where pl.item.id = :itemId order by pl.purchase.date DESC")
    fun findByItemIdOrderByPurchaseDateDesc(@Param("itemId") itemId: Long): List<PurchaseLine>

}

