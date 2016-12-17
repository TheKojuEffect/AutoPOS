package com.kapilkoju.autopos.kernel.api

import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.HttpStatus._
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.util.Assert
import org.springframework.web.bind.annotation._

abstract class CrudApi[T <: AuditableBaseEntity](override protected val repo: AuditableBaseRepository[T],
                                                 override protected val entityName: String,
                                                 override protected val baseUrl: String)
  extends Api[T](repo, entityName, baseUrl) {

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


  @GetMapping(value = Array("/{id}"))
  @Timed
  def get(@PathVariable("id") entityOp: T) = {
    Option(entityOp) match {
      case Some(entity) => new ResponseEntity(entity, OK)
      case None => new ResponseEntity(NOT_FOUND)
    }
  }

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
