package com.kapilkoju.autopos.trade.sale.service

import com.kapilkoju.autopos.trade.sale.domain.Sale
import com.kapilkoju.autopos.trade.sale.domain.SaleLine
import com.kapilkoju.autopos.trade.sale.dto.CreateSaleDto
import com.kapilkoju.autopos.trade.sale.dto.GetSaleDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SaleService {

    fun getSalesWithStatus(getSaleDto: GetSaleDto, pageable: Pageable): Page<Sale>

    fun createNewSale(createSaleDto: CreateSaleDto): Sale

    fun updateSale(sale: Sale): Sale

    fun deleteSale(sale: Sale)

    fun addSaleLine(sale: Sale, saleLine: SaleLine): SaleLine

    fun updateSaleLine(sale: Sale, saleLine: SaleLine): SaleLine

    fun deleteSaleLine(saleLine: SaleLine)

}
