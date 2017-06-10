package com.kapilkoju.autopos.catalog.service

import com.kapilkoju.autopos.catalog.domain.Category
import com.kapilkoju.autopos.kernel.service.AuditableRepository

trait CategoryRepo extends AuditableRepository[Category]
