package com.kapilkoju.autopos.stockbook.api

import com.kapilkoju.autopos.kernel.api.BaseApi
import com.kapilkoju.autopos.stockbook.domain.StockBookEntry
import com.kapilkoju.autopos.stockbook.service.StockBookEntryRepo
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = [StockBookEntryApi.baseUrl])
class StockBookEntryApi(private val stockBookRepo: StockBookEntryRepo)
    : BaseApi<StockBookEntry>(stockBookRepo, "stockBookEntry", StockBookEntryApi.baseUrl) {

    companion object {
        const val baseUrl = "/api/stock-book-entries"
    }
}


