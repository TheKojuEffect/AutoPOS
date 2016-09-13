package io.koju.autopos.kernel.api

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.kernel.domain.AuditableBaseEntity
import io.koju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{DeleteMapping, PathVariable}

trait DeleteApi[T <: AuditableBaseEntity] {
  this: Api[T] =>

  @DeleteMapping(value = Array("/{id}"))
  @Timed
  def delete(@PathVariable("id") id: Long): ResponseEntity[Void] = {
    repo.delete(id)
    ResponseEntity
      .ok
      .headers(HeaderUtil.createEntityDeletionAlert(entityName, id.toString))
      .build()
  }
}
