package com.kapilkoju.autopos.accounting.repo

import com.kapilkoju.autopos.accounting.domain.Ledger
import com.kapilkoju.autopos.kernel.service.AuditableRepository

trait LedgerRepo extends AuditableRepository[Ledger]
