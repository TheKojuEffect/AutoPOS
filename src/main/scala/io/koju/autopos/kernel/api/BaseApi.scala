package io.koju.autopos.kernel.api

import java.lang.Long

import io.koju.autopos.kernel.domain.AuditableBaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RequestMapping(produces = Array(MediaType.APPLICATION_JSON_VALUE))
abstract class BaseApi[T <: AuditableBaseEntity](override protected val repo: JpaRepository[T, Long],
                                                 override protected val entityName: String,
                                                 override protected val baseUrl: String)
  extends CrudApi[T](repo, entityName, baseUrl)
    with GetAllApi[T]