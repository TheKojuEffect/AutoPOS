package com.kapilkoju.autopos.transaction.service

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.transaction.domain.Receipt

trait ReceiptRepo extends AuditableBaseRepository[Receipt]
