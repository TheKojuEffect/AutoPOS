package com.kapilkoju.autopos.catalog.service

import com.kapilkoju.autopos.catalog.domain.Category
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository

trait CategoryRepo extends AuditableBaseRepository[Category]
