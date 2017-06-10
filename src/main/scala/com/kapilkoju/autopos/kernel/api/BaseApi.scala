package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.kernel.service.AuditableRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RequestMapping(produces = Array(MediaType.APPLICATION_JSON_VALUE))
abstract class BaseApi[T <: AuditableEntity](override protected val repo: AuditableRepository[T],
                                             override protected val entityName: String,
                                             override protected val baseUrl: String)
  extends CrudApi[T](repo, entityName, baseUrl)
    with GetAllApi[T]
