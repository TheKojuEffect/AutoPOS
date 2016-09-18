package io.koju.autopos.accounting.api

import io.koju.autopos.accounting.domain.DayBookEntry
import io.koju.autopos.accounting.repo.DayBookEntryRepo
import io.koju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/day-book-entries"))
class DayBookEntryApi(private val dayBookEntryRepo: DayBookEntryRepo)
  extends BaseApi[DayBookEntry](dayBookEntryRepo, "dayBookEntry", "/api/day-book-entries/") {

}
