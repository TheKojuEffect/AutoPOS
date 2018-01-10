package com.kapilkoju.autopos.trade.sale.service

import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.trade.sale.domain.Sale
import com.kapilkoju.autopos.trade.sale.domain.SaleLine
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SaleService {

    fun getSalesWithStatus(status: SaleStatus, pageable: Pageable): Page<Sale>

    fun createNewSale(): Sale

    fun updateSale(sale: Sale): Sale

    fun deleteSale(sale: Sale)

    fun addSaleLine(sale: Sale, saleLine: SaleLine): SaleLine

    fun updateSaleLine(sale: Sale, saleLine: SaleLine): SaleLine

    fun deleteSaleLine(saleLine: SaleLine)

}
