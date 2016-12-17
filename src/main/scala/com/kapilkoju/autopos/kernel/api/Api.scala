package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository

abstract class Api[T <: AuditableBaseEntity](protected val repo: AuditableBaseRepository[T],
                                             protected val entityName: String,
                                             protected val baseUrl: String) {
}
