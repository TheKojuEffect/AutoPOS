package com.kapilkoju.autopos.trade.sale.service

import java.time.LocalDateTime

import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.catalog.service.ItemService
import com.kapilkoju.autopos.trade.sale.domain.{Sale, SaleLine}
import com.kapilkoju.autopos.trade.sale.repo.{SaleLineRepo, SaleRepo}
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaleServiceImpl(private val saleRepo: SaleRepo,
                      private val saleLineRepo: SaleLineRepo,
                      private val itemService: ItemService)
  extends SaleService {

  override def getSalesWithStatus(status: SaleStatus, pageable: Pageable) =
    saleRepo.findByStatus(status, pageable)

  override def createNewSale() = {
    val sale = new Sale
    sale.setDate(LocalDateTime.now())
    sale.setStatus(SaleStatus.PENDING)
    saleRepo.save(sale)
  }

  override def updateSale(sale: Sale) = {
    saleRepo.save(sale)
  }

  override def addSaleLine(sale: Sale, saleLine: SaleLine): SaleLine = {
    saleLine.setSale(sale)
    saleLine.setId(null)
    saleLineRepo.save(saleLine)
    itemService.subtractQuantity(saleLine.getItem, saleLine.getQuantity)
    saleLine
  }

  override def updateSaleLine(sale: Sale, saleLine: SaleLine): SaleLine = {
    saleLine.setSale(sale)

    val dbSaleLine = saleLineRepo.findOne(saleLine.getId)
    val quantityChanged = saleLine.getQuantity - dbSaleLine.getQuantity

    saleLineRepo.save(saleLine)
    itemService.adjustQuantity(saleLine.getItem, -quantityChanged)
    saleLine
  }

  override def deleteSaleLine(saleLine: SaleLine): Unit = {
    saleLineRepo.delete(saleLine)
    itemService.addQuantity(saleLine.getItem, saleLine.getQuantity)
  }
}
