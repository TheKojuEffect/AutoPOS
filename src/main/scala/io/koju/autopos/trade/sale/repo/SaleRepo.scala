package io.koju.autopos.trade.sale.repo

import io.koju.autopos.catalog.domain.SaleStatus
import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.trade.sale.domain.Sale
import org.springframework.data.domain.{Page, Pageable}

trait SaleRepo extends AuditableBaseRepository[Sale] {

  def findByStatus(status: SaleStatus, pageable: Pageable): Page[Sale]

}