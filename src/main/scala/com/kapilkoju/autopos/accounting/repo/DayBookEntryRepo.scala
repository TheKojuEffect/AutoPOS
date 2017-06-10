package com.kapilkoju.autopos.accounting.repo

import com.kapilkoju.autopos.accounting.domain.DayBookEntry
import com.kapilkoju.autopos.kernel.service.AuditableRepository

trait DayBookEntryRepo extends AuditableRepository[DayBookEntry]
