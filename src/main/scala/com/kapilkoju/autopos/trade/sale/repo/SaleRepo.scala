package com.kapilkoju.autopos.trade.sale.repo

import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.trade.sale.domain.Sale
import org.springframework.data.domain.{Page, Pageable}

trait SaleRepo extends AuditableRepository[Sale] {

  def findByStatus(status: SaleStatus, pageable: Pageable): Page[Sale]

}
