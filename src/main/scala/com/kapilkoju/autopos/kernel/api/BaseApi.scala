package com.kapilkoju.autopos.kernel.api

import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RequestMapping(produces = Array(MediaType.APPLICATION_JSON_VALUE))
abstract class BaseApi[T <: AuditableBaseEntity](override protected val repo: AuditableBaseRepository[T],
                                                 override protected val entityName: String,
                                                 override protected val baseUrl: String)
  extends CrudApi[T](repo, entityName, baseUrl)
    with GetAllApi[T]