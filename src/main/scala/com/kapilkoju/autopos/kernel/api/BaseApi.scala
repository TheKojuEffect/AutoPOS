package com.kapilkoju.autopos.kernel.api

import java.util

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.kernel.domain.AuditableBaseEntity
import com.kapilkoju.autopos.kernel.service.AuditableBaseRepository
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.web.bind.annotation._

@RequestMapping(produces = Array(MediaType.APPLICATION_JSON_VALUE))
abstract class BaseApi[T <: AuditableBaseEntity](override protected val repo: AuditableBaseRepository[T],
                                                 override protected val entityName: String,
                                                 override protected val baseUrl: String)
  extends CrudApi[T](repo, entityName, baseUrl) {

  @GetMapping
  @Timed
  def getAll(pageable: Pageable): ResponseEntity[util.List[T]] = {
    val page = repo.findAll(pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }
}
