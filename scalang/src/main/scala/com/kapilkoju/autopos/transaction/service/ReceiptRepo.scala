package com.kapilkoju.autopos.transaction.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.transaction.domain.Receipt

trait ReceiptRepo extends AuditableRepository[Receipt]
