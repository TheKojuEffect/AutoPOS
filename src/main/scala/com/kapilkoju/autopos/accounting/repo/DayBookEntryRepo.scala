package com.kapilkoju.autopos.accounting.repo

import com.kapilkoju.autopos.accounting.domain.DayBookEntry
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository

trait DayBookEntryRepo extends AuditableBaseRepository[DayBookEntry]
