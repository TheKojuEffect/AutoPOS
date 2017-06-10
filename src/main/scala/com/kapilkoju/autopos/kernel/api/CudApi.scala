package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.kernel.service.AuditableRepository

abstract class CudApi[T <: AuditableEntity](override protected val repo: AuditableRepository[T],
                                            override protected val entityName: String,
                                            override protected val baseUrl: String)
  extends Api[T](repo, entityName, baseUrl)
    with CreateApi[T]
    with UpdateApi[T]
    with DeleteApi[T]
