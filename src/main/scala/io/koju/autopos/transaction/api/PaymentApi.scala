package io.koju.autopos.transaction.api

import io.koju.autopos.kernel.api.BaseApi
import io.koju.autopos.transaction.domain.Payment
import io.koju.autopos.transaction.repo.PaymentRepo
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/payments"))
class PaymentApi(private val paymentRepo: PaymentRepo)
  extends BaseApi[Payment](paymentRepo, "payment", "/api/payments")