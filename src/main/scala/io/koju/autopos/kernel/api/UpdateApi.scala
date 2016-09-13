package io.koju.autopos.kernel.api

import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.kernel.domain.AuditableBaseEntity
import io.koju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.{PathVariable, PutMapping, RequestBody}

trait UpdateApi[T <: AuditableBaseEntity] {
  this: Api[T] =>

  @PutMapping(value = Array("/{id}"))
  @Timed
  def update(@PathVariable("id") id: Long, @RequestBody @Valid entity: T): ResponseEntity[T] = {

    Assert.isTrue(entity.getId == id)

    val updatedEntity = repo.save(entity)
    ResponseEntity
      .ok
      .headers(HeaderUtil.createEntityUpdateAlert(entityName, entity.getId.toString))
      .body(updatedEntity)
  }

  @PutMapping
  @Timed
  def updateEntity(@RequestBody @Valid entity: T): ResponseEntity[T] = {
    Assert.isTrue(entity.getId != null)
    update(entity.getId, entity)
  }
}
