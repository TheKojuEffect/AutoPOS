package io.koju.autopos.accounting.api

import io.koju.autopos.accounting.domain.LedgerEntry
import io.koju.autopos.accounting.repo.LedgerEntryRepo
import io.koju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/ledger-entries"))
class LedgerEntryApi(private val ledgerEntryRepo: LedgerEntryRepo)
  extends BaseApi[LedgerEntry](ledgerEntryRepo, "ledgerEntry", "/api/ledger-entries/")
