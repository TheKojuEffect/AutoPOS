package com.kapilkoju.autopos.trade.sale.repo

import com.kapilkoju.autopos.catalog.domain.SaleStatus
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.trade.sale.domain.Sale
import org.springframework.data.domain.{Page, Pageable}

trait SaleRepo extends AuditableBaseRepository[Sale] {

  def findByStatus(status: SaleStatus, pageable: Pageable): Page[Sale]

}
