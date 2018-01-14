package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.trade.purchase.domain.Purchase
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PurchaseRepo : AuditableRepository<Purchase> {
    fun findAllByVat(vat: Boolean, pageable: Pageable): Page<Purchase>
}
