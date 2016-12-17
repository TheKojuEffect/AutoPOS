package com.kapilkoju.autopos.catalog.repo

import com.kapilkoju.autopos.catalog.domain.Category
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository

trait CategoryRepo extends AuditableBaseRepository[Category]
