package com.kapilkoju.autopos.accounting.api

import com.kapilkoju.autopos.accounting.domain.DayBookEntry
import com.kapilkoju.autopos.accounting.repo.DayBookEntryRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/day-book-entries"))
class DayBookEntryApi(private val dayBookEntryRepo: DayBookEntryRepo)
  extends BaseApi[DayBookEntry](dayBookEntryRepo, "dayBookEntry", "/api/day-book-entries/")
