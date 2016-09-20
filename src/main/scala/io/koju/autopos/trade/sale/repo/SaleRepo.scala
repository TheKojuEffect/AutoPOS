package io.koju.autopos.trade.sale.repo

import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.trade.sale.domain.Sale
import org.springframework.data.domain.{Page, Pageable}

trait SaleRepo extends AuditableBaseRepository[Sale] {

  def findByStatus(status: Sale.Status, pageable: Pageable): Page[Sale]

}