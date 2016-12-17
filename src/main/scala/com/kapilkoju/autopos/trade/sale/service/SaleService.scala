package com.kapilkoju.autopos.trade.sale.service

import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.trade.sale.domain.{Sale, SaleLine}
import org.springframework.data.domain.{Page, Pageable}

trait SaleService {

  def getSalesWithStatus(status: SaleStatus, pageable: Pageable): Page[Sale]

  def createNewSale(): Sale

  def updateSale(sale: Sale): Sale

  def addSaleLine(sale: Sale, saleLine: SaleLine): SaleLine

  def updateSaleLine(sale: Sale, saleLine: SaleLine): SaleLine

  def deleteSaleLine(saleLine: SaleLine): Unit

}
