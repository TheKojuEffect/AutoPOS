package io.koju.autopos.kernel.api

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.kernel.domain.AuditableBaseEntity
import org.springframework.http.HttpStatus.{NOT_FOUND, OK}
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable}

trait GetApi[T <: AuditableBaseEntity] {
  this: Api[T] =>

  @GetMapping(value = Array("/{id}"))
  @Timed
  def get(@PathVariable("id") entityOp: T) = {
    Option(entityOp) match {
      case Some(entity) => new ResponseEntity(entity, OK)
      case None => new ResponseEntity(NOT_FOUND)
    }
  }
}
