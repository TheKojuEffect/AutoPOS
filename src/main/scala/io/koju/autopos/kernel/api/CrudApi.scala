package io.koju.autopos.kernel.api

import java.lang.Long

import io.koju.autopos.kernel.domain.AuditableBaseEntity
import org.springframework.data.jpa.repository.JpaRepository

abstract class CrudApi[T <: AuditableBaseEntity](override protected val repo: JpaRepository[T, Long],
                                                 override protected val entityName: String,
                                                 override protected val baseUrl: String)
  extends Api[T](repo, entityName, baseUrl)
    with CreateApi[T]
    with GetApi[T]
    with UpdateApi[T]
    with DeleteApi[T]
