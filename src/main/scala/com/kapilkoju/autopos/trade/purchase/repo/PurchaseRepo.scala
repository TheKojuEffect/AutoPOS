package com.kapilkoju.autopos.trade.purchase.repo

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.trade.purchase.domain.Purchase

trait PurchaseRepo extends AuditableBaseRepository[Purchase]
