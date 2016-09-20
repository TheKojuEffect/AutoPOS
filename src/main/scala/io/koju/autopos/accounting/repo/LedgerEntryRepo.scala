package io.koju.autopos.accounting.repo

import io.koju.autopos.accounting.domain.LedgerEntry
import io.koju.autopos.kernel.service.AuditableBaseRepository

trait LedgerEntryRepo extends AuditableBaseRepository[LedgerEntry]