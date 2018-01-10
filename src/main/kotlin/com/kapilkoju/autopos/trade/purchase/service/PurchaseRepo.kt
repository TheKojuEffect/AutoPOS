package com.kapilkoju.autopos.trade.purchase.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.trade.purchase.domain.Purchase

interface PurchaseRepo : AuditableRepository<Purchase>
