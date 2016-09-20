package io.koju.autopos.transaction.repo

import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.transaction.domain.Receipt

trait ReceiptRepo extends AuditableBaseRepository[Receipt]