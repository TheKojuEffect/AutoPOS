package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.kernel.service.AuditableRepository

abstract class CrudApi<T : AuditableEntity>(override val repo: AuditableRepository<T>,
                                            override val entityName: String,
                                            override val baseUrl: String)
    : Api<T>, CreateApi<T>, GetApi<T>, UpdateApi<T>, DeleteApi<T>
