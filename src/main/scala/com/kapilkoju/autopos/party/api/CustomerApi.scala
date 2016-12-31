package com.kapilkoju.autopos.party.api

import java.util
import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.party.api.CustomerApi.baseUrl
import com.kapilkoju.autopos.party.domain.Customer
import com.kapilkoju.autopos.party.service.{CustomerRepo, CustomerService}
import com.kapilkoju.autopos.web.rest.util.{HeaderUtil, PaginationUtil}
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.{NOT_FOUND, OK}
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.util.Assert
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array(baseUrl))
class CustomerApi(repo: CustomerRepo, customerService: CustomerService) {

  final val entityName = "customer"

  @GetMapping(value = Array("/{id}"))
  @Timed
  def get(@PathVariable("id") id: Long): ResponseEntity[_ <: Customer] = {
    customerService.getCustomer(id) match {
      case Some(customer) => new ResponseEntity(customer, OK)
      case None => new ResponseEntity(NOT_FOUND)
    }
  }

  @GetMapping
  @Timed
  def getAll(pageable: Pageable): ResponseEntity[util.List[Customer]] = {
    val page = repo.findAll(pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

  @PostMapping
  @Timed
  def save(@RequestBody @Valid customer: Customer): ResponseEntity[Customer] = {

    if (customer.getId != null) {
      val failureHeaders = HeaderUtil.createFailureAlert(entityName,
        "idexists", s"A new $entityName cannot already have an ID")
      return new ResponseEntity[Customer](failureHeaders, HttpStatus.BAD_REQUEST)
    }

    val savedEntity = repo.save(customer)
    ResponseEntity
      .ok
      .headers(HeaderUtil.createEntityCreationAlert(entityName, savedEntity.getId.toString))
      .body(savedEntity)
  }


  @PutMapping(value = Array("/{id}"))
  @Timed
  def update(@PathVariable("id") id: Long, @RequestBody @Valid customer: Customer): ResponseEntity[Customer] = {

    Assert.isTrue(customer.getId == id)

    val updatedEntity = repo.save(customer)
    ResponseEntity
      .ok
      .headers(HeaderUtil.createEntityUpdateAlert(entityName, customer.getId.toString))
      .body(updatedEntity)
  }

  @PutMapping
  @Timed
  def updateEntity(@RequestBody @Valid customer: Customer): ResponseEntity[Customer] = {
    Assert.isTrue(customer.getId != null)
    update(customer.getId, customer)
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

object CustomerApi {
  final val baseUrl = "/api/customers"
}