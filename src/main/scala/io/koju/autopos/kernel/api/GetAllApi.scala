package io.koju.autopos.kernel.api

import java.util

import com.codahale.metrics.annotation.Timed
import io.koju.autopos.kernel.domain.AuditableBaseEntity
import io.koju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.GetMapping

trait GetAllApi[T <: AuditableBaseEntity] {
  this: Api[T] =>

  @GetMapping
  @Timed
  def getAll(pageable: Pageable): ResponseEntity[util.List[T]] = {
    val page = repo.findAll(pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }
}
