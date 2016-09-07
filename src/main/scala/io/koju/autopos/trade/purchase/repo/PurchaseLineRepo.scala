package io.koju.autopos.trade.purchase.repo

import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.trade.purchase.domain.PurchaseLine

trait PurchaseLineRepo extends AuditableBaseRepository[PurchaseLine]

