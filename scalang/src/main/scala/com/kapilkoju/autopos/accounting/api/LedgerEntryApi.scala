package com.kapilkoju.autopos.accounting.api

import com.kapilkoju.autopos.accounting.repo.LedgerEntryRepo
import com.kapilkoju.autopos.accounting.domain.LedgerEntry
import com.kapilkoju.autopos.accounting.repo.LedgerEntryRepo
import com.kapilkoju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/ledger-entries"))
class LedgerEntryApi(private val ledgerEntryRepo: LedgerEntryRepo)
  extends BaseApi[LedgerEntry](ledgerEntryRepo, "ledgerEntry", "/api/ledger-entries/")
