package io.koju.autopos.kernel.api

import io.koju.autopos.kernel.domain.AuditableBaseEntity
import io.koju.autopos.kernel.service.AuditableBaseRepository

abstract class Api[T <: AuditableBaseEntity](protected val repo: AuditableBaseRepository[T],
                                             protected val entityName: String,
                                             protected val baseUrl: String) {
}