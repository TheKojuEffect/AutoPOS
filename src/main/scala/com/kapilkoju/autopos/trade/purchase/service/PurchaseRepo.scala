package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.trade.purchase.domain.Purchase

trait PurchaseRepo extends AuditableBaseRepository[Purchase]
