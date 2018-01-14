package com.kapilkoju.autopos.stockbook.service

import com.kapilkoju.autopos.catalog.domain.Item
import com.kapilkoju.autopos.kernel.service.AuditableRepository
import com.kapilkoju.autopos.stockbook.domain.StockBookEntry

interface StockBookEntryRepo : AuditableRepository<StockBookEntry> {
    fun findOneByItem(item: Item): StockBookEntry?
}
