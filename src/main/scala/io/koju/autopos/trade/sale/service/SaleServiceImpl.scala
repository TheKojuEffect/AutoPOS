package io.koju.autopos.trade.sale.service

import java.time.LocalDateTime

import io.koju.autopos.catalog.service.ItemService
import io.koju.autopos.trade.sale.domain.Sale.Status
import io.koju.autopos.trade.sale.domain.{QSale, Sale, SaleLine}
import io.koju.autopos.trade.sale.repo.{SaleLineRepo, SaleRepo}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaleServiceImpl(private val saleRepo: SaleRepo,
                      private val saleLineRepo: SaleLineRepo,
                      private val itemService: ItemService)
  extends SaleService {

  private val qSale = QSale.sale

  override def getSalesWithStatus(status: Status, pageable: Pageable): Page[Sale] =
    saleRepo.findAll(qSale.status eq status, pageable)

  override def createNewSale() = {
    val sale = new Sale
    sale.setDate(LocalDateTime.now())
    sale.setStatus(Sale.Status.PENDING)
    saleRepo.save(sale)
  }

  override def updateSale(sale: Sale) = {
    saleRepo.save(sale)
  }

  override def addSaleLine(sale: Sale, saleLine: SaleLine): SaleLine = {
    saleLine.setSale(sale)
    saleLine.setId(null)
    saleLineRepo.save(saleLine)
    itemService.substractQuantity(saleLine.getItem, saleLine.getQuantity)
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
