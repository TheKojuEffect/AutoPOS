package io.koju.autopos.kernel.api

import java.lang.Long

import io.koju.autopos.kernel.domain.AuditableBaseEntity
import org.springframework.data.jpa.repository.JpaRepository

abstract class Api[T <: AuditableBaseEntity](protected val repo: JpaRepository[T, Long],
                                             protected val entityName: String,
                                             protected val baseUrl: String) {
}