package com.kapilkoju.autopos.trade.sale.service

import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.catalog.service.ItemService
import com.kapilkoju.autopos.stockbook.service.StockBookService
import com.kapilkoju.autopos.trade.sale.domain.Sale
import com.kapilkoju.autopos.trade.sale.domain.SaleLine
import com.kapilkoju.autopos.trade.sale.dto.CreateSaleDto
import com.kapilkoju.autopos.trade.sale.dto.GetSaleDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class SaleServiceImpl(private val saleRepo: SaleRepo,
                      private val saleLineRepo: SaleLineRepo,
                      private val itemService: ItemService,
                      private val stockBookService: StockBookService)
    : SaleService {

    override fun getSalesWithStatus(getSaleDto: GetSaleDto, pageable: Pageable): Page<Sale> =
            saleRepo.findByStatusAndVat(getSaleDto.status, getSaleDto.vat, pageable)

    override fun createNewSale(createSaleDto: CreateSaleDto): Sale {
        val sale = Sale()
        sale.date = Instant.now()
        sale.status = SaleStatus.PENDING
        sale.vat = createSaleDto.vat
        return saleRepo.save(sale)
    }

    override fun updateSale(sale: Sale): Sale {
        return saleRepo.save(sale)
    }

    override fun addSaleLine(sale: Sale, saleLine: SaleLine): SaleLine {
        saleLine.sale = sale
        saleLine.setId(null)
        saleLineRepo.save(saleLine)
        if (sale.vat) {
            stockBookService.subtractStock(saleLine.item, saleLine.quantity)
        } else {
            itemService.subtractQuantity(saleLine.item, saleLine.quantity)
        }
        return saleLine
    }

    override fun updateSaleLine(sale: Sale, saleLine: SaleLine): SaleLine {
        saleLine.sale = sale

        val dbSaleLine = saleLineRepo.findOne(saleLine.getId())
        val quantityChanged = saleLine.quantity - dbSaleLine.quantity

        val updatedSaleLine = saleLineRepo.save(saleLine)
        if (sale.vat) {
            stockBookService.adjustStock(saleLine.item, -quantityChanged)
        } else {
            itemService.adjustQuantity(saleLine.item, -quantityChanged)
        }
        return updatedSaleLine
    }

    override fun deleteSaleLine(saleLine: SaleLine) {
        saleLineRepo.delete(saleLine)
        if (saleLine.sale.vat) {
            stockBookService.addStock(saleLine.item, saleLine.quantity)
        } else {
            itemService.addQuantity(saleLine.item, saleLine.quantity)
        }
    }

    override fun deleteSale(sale: Sale) {
        saleRepo.delete(sale)
    }
}
