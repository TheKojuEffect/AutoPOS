package com.kapilkoju.autopos.transaction.api

import com.kapilkoju.autopos.kernel.api.BaseApi
import com.kapilkoju.autopos.transaction.domain.Receipt
import com.kapilkoju.autopos.transaction.service.ReceiptRepo
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ReceiptApi.baseUrl)
class ReceiptApi(private val receiptRepo: ReceiptRepo)
    : BaseApi<Receipt>(receiptRepo, "receipt", baseUrl) {

    companion object {
        const val baseUrl = "/api/receipts"
    }
}
