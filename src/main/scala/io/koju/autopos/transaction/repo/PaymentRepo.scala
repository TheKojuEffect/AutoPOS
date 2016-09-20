package io.koju.autopos.transaction.repo

import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.transaction.domain.Payment

trait PaymentRepo extends AuditableBaseRepository[Payment]