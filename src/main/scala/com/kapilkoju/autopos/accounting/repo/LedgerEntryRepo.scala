package com.kapilkoju.autopos.accounting.repo

import com.kapilkoju.autopos.accounting.domain.LedgerEntry
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository

trait LedgerEntryRepo extends AuditableBaseRepository[LedgerEntry]
