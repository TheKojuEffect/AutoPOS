package io.koju.autopos.trade.sale.service

import io.koju.autopos.catalog.domain.SaleStatus
import io.koju.autopos.trade.sale.domain.{Sale, SaleLine}
import org.springframework.data.domain.{Page, Pageable}

trait SaleService {

  def getSalesWithStatus(status: SaleStatus, pageable: Pageable): Page[Sale]

  def createNewSale(): Sale

  def updateSale(sale: Sale): Sale

  def addSaleLine(sale: Sale, saleLine: SaleLine): SaleLine

  def updateSaleLine(sale: Sale, saleLine: SaleLine): SaleLine

  def deleteSaleLine(saleLine: SaleLine): Unit

}
