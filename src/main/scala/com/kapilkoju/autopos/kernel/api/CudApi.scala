package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository

abstract class CudApi[T <: AuditableBaseEntity](override protected val repo: AuditableBaseRepository[T],
                                                override protected val entityName: String,
                                                override protected val baseUrl: String)
  extends Api[T](repo, entityName, baseUrl)
    with CreateApi[T]
    with UpdateApi[T]
    with DeleteApi[T]
