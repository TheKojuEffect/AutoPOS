package io.koju.autopos.accounting.api

import io.koju.autopos.accounting.domain.Ledger
import io.koju.autopos.accounting.repo.LedgerRepo
import io.koju.autopos.kernel.api.BaseApi
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/ledgers"))
class LedgerApi(private val ledgerRepo: LedgerRepo)
  extends BaseApi[Ledger](ledgerRepo, "ledger", "/api/ledgers/")