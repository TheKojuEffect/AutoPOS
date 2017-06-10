package com.kapilkoju.autopos.kernel.api

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import org.springframework.http.HttpStatus.{NOT_FOUND, OK}
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable}

trait GetApi[T <: AuditableEntity] {
  this: Api[T] =>

  @GetMapping(value = Array("/{id}"))
  @Timed
  def get(@PathVariable("id") entityOp: T): ResponseEntity[_ <: T] = {
    Option(entityOp) match {
      case Some(entity) => new ResponseEntity(entity, OK)
      case None => new ResponseEntity(NOT_FOUND)
    }
  }
}
