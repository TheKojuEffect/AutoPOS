package io.koju.autopos.party.repo

import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.party.domain.Customer

trait CustomerRepo extends AuditableBaseRepository[Customer]