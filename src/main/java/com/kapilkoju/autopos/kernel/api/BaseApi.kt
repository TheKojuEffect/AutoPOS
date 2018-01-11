package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.kernel.service.AuditableRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
abstract class BaseApi<T : AuditableEntity>(override val repo: AuditableRepository<T>,
                                            override val entityName: String,
                                            override val baseUrl: String)
    : CrudApi<T>(repo, entityName, baseUrl), GetAllApi<T>