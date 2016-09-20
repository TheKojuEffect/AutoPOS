package io.koju.autopos.transaction.api

import io.koju.autopos.kernel.api.BaseApi
import io.koju.autopos.transaction.domain.Receipt
import io.koju.autopos.transaction.repo.ReceiptRepo
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/receipts"))
class ReceiptApi(private val receiptRepo: ReceiptRepo)
  extends BaseApi[Receipt](receiptRepo, "receipt", "/api/receipts")