package io.koju.autopos.party.api

import java.util.List
import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.kernel.domain.AuditableBaseEntity
import io.koju.autopos.kernel.service.AuditableBaseRepository
import io.koju.autopos.web.rest.util.{HeaderUtil, PaginationUtil}
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus._
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.util.Assert
import org.springframework.web.bind.annotation._

@RequestMapping(produces = Array(MediaType.APPLICATION_JSON_VALUE))
abstract class BaseApi[T <: AuditableBaseEntity](protected val repo: AuditableBaseRepository[T],
                                                 protected val entityName: String,
                                                 protected val baseUrl: String) {

  @GetMapping
  @Timed
  def getAll(pageable: Pageable): ResponseEntity[List[T]] = {
    val page = repo.findAll(pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }


  @GetMapping(value = Array("/{id}"))
  @Timed
  def get(@PathVariable("id") entityOp: T) = {
    Option(entityOp) match {
      case Some(entity) => new ResponseEntity(entity, OK)
      case None => new ResponseEntity(NOT_FOUND)
    }
  }


  @PostMapping
  @Timed
  def save(@RequestBody @Valid entity: T): ResponseEntity[T] = {

    if (entity.getId != null) {
      val failureHeaders = HeaderUtil.createFailureAlert(entityName,
        "idexists", s"A new ${entityName} cannot already have an ID")
      return new ResponseEntity[T](failureHeaders, HttpStatus.BAD_REQUEST)
    }

    val savedEntity = repo.save(entity)
    ResponseEntity
      .ok
      .headers(HeaderUtil.createEntityCreationAlert(entityName, savedEntity.getId.toString))
      .body(savedEntity)
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
