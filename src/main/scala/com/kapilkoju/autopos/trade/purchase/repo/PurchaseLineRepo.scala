package com.kapilkoju.autopos.trade.purchase.repo

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.trade.purchase.domain.PurchaseLine

trait PurchaseLineRepo extends AuditableBaseRepository[PurchaseLine]

