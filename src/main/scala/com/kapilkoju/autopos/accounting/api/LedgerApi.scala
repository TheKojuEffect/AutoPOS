package com.kapilkoju.autopos.accounting.api

import com.kapilkoju.autopos.accounting.domain.Ledger
import com.kapilkoju.autopos.accounting.repo.LedgerRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/ledgers"))
class LedgerApi(private val ledgerRepo: LedgerRepo)
  extends BaseApi[Ledger](ledgerRepo, "ledger", "/api/ledgers/")
