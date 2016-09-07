package io.koju.autopos.trade.purchase.repo

import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.trade.purchase.domain.Purchase

trait PurchaseRepo extends AuditableBaseRepository[Purchase]