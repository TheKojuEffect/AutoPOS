package com.kapilkoju.autopos.transaction.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.transaction.domain.Receipt

interface ReceiptRepo : AuditableRepository<Receipt>
