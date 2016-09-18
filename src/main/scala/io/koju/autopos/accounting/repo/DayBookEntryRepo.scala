package io.koju.autopos.accounting.repo

import io.koju.autopos.accounting.domain.DayBookEntry
import io.koju.autopos.kernel.service.AuditableBaseRepository

trait DayBookEntryRepo extends AuditableBaseRepository[DayBookEntry]