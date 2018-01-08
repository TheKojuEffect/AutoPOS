package com.kapilkoju.autopos.accounting.service

import com.kapilkoju.autopos.accounting.domain.DayBookEntry
import com.kapilkoju.autopos.kernel.service.AuditableRepository
import org.springframework.stereotype.Repository

@Repository
interface DayBookEntryRepo : AuditableRepository<DayBookEntry>
