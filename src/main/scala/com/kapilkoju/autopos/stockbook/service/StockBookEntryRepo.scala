package com.kapilkoju.autopos.stockbook.service

import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.stockbook.domain.StockBookEntry

trait StockBookEntryRepo extends AuditableRepository[StockBookEntry] {
}
