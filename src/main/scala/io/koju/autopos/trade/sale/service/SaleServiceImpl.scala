package io.koju.autopos.trade.sale.service

import io.koju.autopos.trade.sale.domain.Sale.Status
import io.koju.autopos.trade.sale.domain.{QSale, Sale}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service

@Service
class SaleServiceImpl(private val saleRepository: SaleRepository) extends SaleService {

  private val qSale = QSale.sale

  override def getSalesWithStatus(status: Status, pageable: Pageable): Page[Sale] =
    saleRepository.findAll(qSale.status eq status, pageable)

}
