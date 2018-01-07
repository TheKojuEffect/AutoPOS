package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.kernel.service.AuditableRepository

abstract class Api[T <: AuditableEntity](protected val repo: AuditableRepository[T],
                                         protected val entityName: String,
                                         protected val baseUrl: String) {
}
