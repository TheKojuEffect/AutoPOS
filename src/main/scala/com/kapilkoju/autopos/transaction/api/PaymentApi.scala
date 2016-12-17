package com.kapilkoju.autopos.transaction.api

import com.kapilkoju.autopos.kernel.api.BaseApi
import com.kapilkoju.autopos.transaction.domain.Payment
import com.kapilkoju.autopos.transaction.repo.PaymentRepo
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/payments"))
class PaymentApi(private val paymentRepo: PaymentRepo)
  extends BaseApi[Payment](paymentRepo, "payment", "/api/payments")
