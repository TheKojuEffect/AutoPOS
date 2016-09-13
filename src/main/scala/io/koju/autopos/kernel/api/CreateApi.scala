package io.koju.autopos.kernel.api

import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.kernel.domain.AuditableBaseEntity
import io.koju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{PostMapping, RequestBody}

trait CreateApi[T <: AuditableBaseEntity] {
  this: Api[T] =>

  @PostMapping
  @Timed
  def save(@RequestBody @Valid entity: T): ResponseEntity[T] = {

    if (entity.getId != null) {
      val failureHeaders = HeaderUtil.createFailureAlert(entityName,
        "idexists", s"A new $entityName cannot already have an ID")
      return new ResponseEntity[T](failureHeaders, HttpStatus.BAD_REQUEST)
    }

    val savedEntity = repo.save(entity)
    ResponseEntity
      .ok
      .headers(HeaderUtil.createEntityCreationAlert(entityName, savedEntity.getId.toString))
      .body(savedEntity)
  }
}
