package io.koju.autopos.kernel.api

import io.koju.autopos.kernel.domain.AuditableBaseEntity
import io.koju.autopos.kernel.service.AuditableBaseRepository

abstract class CrudApi[T <: AuditableBaseEntity](override protected val repo: AuditableBaseRepository[T],
                                                 override protected val entityName: String,
                                                 override protected val baseUrl: String)
  extends Api[T](repo, entityName, baseUrl)
    with CreateApi[T]
    with GetApi[T]
    with UpdateApi[T]
    with DeleteApi[T]
