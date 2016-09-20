package io.koju.autopos.accounting.repo

import io.koju.autopos.accounting.domain.Ledger
import io.koju.autopos.kernel.service.AuditableBaseRepository

trait LedgerRepo extends AuditableBaseRepository[Ledger]