package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.kernel.service.AuditableRepository

interface Api<T : AuditableEntity> {
    val repo: AuditableRepository<T>
    val entityName: String
    val baseUrl: String
}