package com.kapilkoju.autopos.party.api

import java.util
import javax.validation.Valid

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.party.api.VendorApi.baseUrl
import com.kapilkoju.autopos.party.domain.Vendor
import com.kapilkoju.autopos.party.service.{VendorRepo, VendorService}
import com.kapilkoju.autopos.web.rest.util.{HeaderUtil, PaginationUtil}
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.{NOT_FOUND, OK}
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.util.Assert
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array(baseUrl))
class VendorApi(repo: VendorRepo, vendorService: VendorService) {

  final val entityName = "vendor"

  @GetMapping(value = Array("/{id}"))
  @Timed
  def get(@PathVariable("id") id: Long): ResponseEntity[_ <: Vendor] = {
    vendorService.getVendor(id) match {
      case Some(vendor) => new ResponseEntity(vendor, OK)
      case None => new ResponseEntity(NOT_FOUND)
    }
  }

  @GetMapping
  @Timed
  def getAll(pageable: Pageable): ResponseEntity[util.List[Vendor]] = {
    val page = repo.findAll(pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    new ResponseEntity(page.getContent, headers, HttpStatus.OK)
  }

  @PostMapping
  @Timed
  def save(@RequestBody @Valid vendor: Vendor): ResponseEntity[Vendor] = {

    if (vendor.getId != null) {
      val failureHeaders = HeaderUtil.createFailureAlert(entityName,
        "idexists", s"A new $entityName cannot already have an ID")
      return new ResponseEntity[Vendor](failureHeaders, HttpStatus.BAD_REQUEST)
    }

    val savedEntity = repo.save(vendor)
    ResponseEntity
      .ok
      .headers(HeaderUtil.createEntityCreationAlert(entityName, savedEntity.getId.toString))
      .body(savedEntity)
  }


  @PutMapping(value = Array("/{id}"))
  @Timed
  def update(@PathVariable("id") id: Long, @RequestBody @Valid vendor: Vendor): ResponseEntity[Vendor] = {

    Assert.isTrue(vendor.getId == id)

    val updatedEntity = repo.save(vendor)
    ResponseEntity
      .ok
      .headers(HeaderUtil.createEntityUpdateAlert(entityName, vendor.getId.toString))
      .body(updatedEntity)
  }

  @PutMapping
  @Timed
  def updateEntity(@RequestBody @Valid vendor: Vendor): ResponseEntity[Vendor] = {
    Assert.isTrue(vendor.getId != null)
    update(vendor.getId, vendor)
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

object VendorApi {
  final val baseUrl = "/api/vendors"
}