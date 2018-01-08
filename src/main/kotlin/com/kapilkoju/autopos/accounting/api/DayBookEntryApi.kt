package com.kapilkoju.autopos.accounting.api


import com.kapilkoju.autopos.accounting.domain.DayBookEntry
import com.kapilkoju.autopos.accounting.service.DayBookEntryRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(DayBookEntryApi.baseUrl)
class DayBookEntryApi(dayBookEntryRepo: DayBookEntryRepo)
    : BaseApi<DayBookEntry>(dayBookEntryRepo, "dayBookEntry", baseUrl) {

    companion object {
        const val baseUrl = "/api/day-book-entries"
    }
}
