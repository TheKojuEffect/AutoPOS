package com.kapilkoju.autopos.trade.sale.service

import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.trade.sale.domain.Sale
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SaleRepo : AuditableRepository<Sale> {

    fun findByStatus(status: SaleStatus, pageable: Pageable): Page<Sale>

}
