package com.kapilkoju.autopos.stockbook.api

import com.kapilkoju.autopos.kernel.api.BaseApi
import com.kapilkoju.autopos.stockbook.service.StockBookEntryRepo
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array(StockBookEntryApi.baseUrl))
class StockBookEntryApi(private val stockBookRepo: StockBookEntryRepo)
  extends BaseApi(stockBookRepo, "stockBookEntry", StockBookEntryApi.baseUrl)

object StockBookEntryApi {
  final val baseUrl = "/api/stock-book-entries"
}
