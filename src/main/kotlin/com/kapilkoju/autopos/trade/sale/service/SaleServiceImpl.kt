package com.kapilkoju.autopos.trade.sale.service

import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.catalog.service.ItemService
import com.kapilkoju.autopos.trade.sale.domain.Sale
import com.kapilkoju.autopos.trade.sale.domain.SaleLine
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class SaleServiceImpl(private val saleRepo: SaleRepo,
                      private val saleLineRepo: SaleLineRepo,
                      private val itemService: ItemService)
    : SaleService {

    override fun getSalesWithStatus(status: SaleStatus, pageable: Pageable): Page<Sale> =
            saleRepo.findByStatus(status, pageable)

    override fun createNewSale(): Sale {
        val sale = Sale()
        sale.date = Instant.now()
        sale.status = SaleStatus.PENDING
        return saleRepo.save(sale)
    }

    override fun updateSale(sale: Sale): Sale {
        return saleRepo.save(sale)
    }


    override fun addSaleLine(sale: Sale, saleLine: SaleLine): SaleLine {
        saleLine.sale = sale
        saleLine.setId(null)
        saleLineRepo.save(saleLine)
        itemService.subtractQuantity(saleLine.item, saleLine.quantity)
        return saleLine
    }

    override fun updateSaleLine(sale: Sale, saleLine: SaleLine): SaleLine {
        saleLine.sale = sale

        val dbSaleLine = saleLineRepo.findOne(saleLine.getId())
        val quantityChanged = saleLine.quantity - dbSaleLine.quantity

        val updatedSaleLine = saleLineRepo.save(saleLine)
        itemService.adjustQuantity(saleLine.item, -quantityChanged)
        return updatedSaleLine
    }

    override fun deleteSaleLine(saleLine: SaleLine) {
        saleLineRepo.delete(saleLine)
        itemService.addQuantity(saleLine.item, saleLine.quantity)
    }

    override fun deleteSale(sale: Sale) {
        saleRepo.delete(sale)
    }
}
