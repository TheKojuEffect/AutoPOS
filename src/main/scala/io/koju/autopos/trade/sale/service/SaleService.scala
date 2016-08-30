package io.koju.autopos.trade.sale.service

import io.koju.autopos.trade.sale.domain.Sale
import org.springframework.data.domain.{Page, Pageable}

trait SaleService {

  def getSalesWithStatus(status: Sale.Status, pageable: Pageable): Page[Sale]

  def createNewSale(): Sale

  def updateSale(sale: Sale): Sale

}
