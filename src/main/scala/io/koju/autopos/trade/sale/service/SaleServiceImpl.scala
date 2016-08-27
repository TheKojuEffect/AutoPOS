package io.koju.autopos.trade.sale.service

import java.time.LocalDateTime

import io.koju.autopos.trade.sale.domain.Sale.Status
import io.koju.autopos.trade.sale.domain.{QSale, Sale}
import io.koju.autopos.trade.sale.repo.SaleRepo
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service

@Service
class SaleServiceImpl(private val saleRepo: SaleRepo) extends SaleService {

  private val qSale = QSale.sale

  override def getSalesWithStatus(status: Status, pageable: Pageable): Page[Sale] =
    saleRepo.findAll(qSale.status eq status, pageable)

  override def createNewSale() = {
    val sale = new Sale
    sale.setDate(LocalDateTime.now())
    sale.setStatus(Sale.Status.PENDING)
    saleRepo.save(sale)
  }
}
