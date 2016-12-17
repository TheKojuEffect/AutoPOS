package com.kapilkoju.autopos.transaction.repo

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.transaction.domain.Payment

trait PaymentRepo extends AuditableBaseRepository[Payment]
