package com.kapilkoju.autopos.party.repo

import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.party.domain.Customer

trait CustomerRepo extends AuditableBaseRepository[Customer]
