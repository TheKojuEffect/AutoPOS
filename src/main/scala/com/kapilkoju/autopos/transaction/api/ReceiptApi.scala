package com.kapilkoju.autopos.transaction.api

import com.kapilkoju.autopos.transaction.domain.Receipt
import com.kapilkoju.autopos.transaction.repo.ReceiptRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import com.kapilkoju.autopos.transaction.domain.Receipt
import com.kapilkoju.autopos.transaction.repo.ReceiptRepo
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/receipts"))
class ReceiptApi(private val receiptRepo: ReceiptRepo)
  extends BaseApi[Receipt](receiptRepo, "receipt", "/api/receipts")
