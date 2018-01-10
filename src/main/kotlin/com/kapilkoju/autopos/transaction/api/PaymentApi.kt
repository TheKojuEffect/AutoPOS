package com.kapilkoju.autopos.transaction.api

import com.kapilkoju.autopos.kernel.api.BaseApi
import com.kapilkoju.autopos.transaction.domain.Payment
import com.kapilkoju.autopos.transaction.service.PaymentRepo
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(PaymentApi.baseUrl)
class PaymentApi(paymentRepo: PaymentRepo)
    : BaseApi<Payment>(paymentRepo, "payment", baseUrl) {

    companion object {
        const val baseUrl = "/api/payments"
    }
}
