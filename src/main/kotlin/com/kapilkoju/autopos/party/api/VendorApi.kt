package com.kapilkoju.autopos.party.api

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.party.domain.Vendor
import com.kapilkoju.autopos.party.service.VendorRepo
import com.kapilkoju.autopos.party.service.VendorService
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(VendorApi.baseUrl)
class VendorApi(private val repo: VendorRepo, private val vendorService: VendorService) {


  @GetMapping("{id}")
  @Timed
  fun get(@PathVariable("id") id: Long): ResponseEntity<Vendor> {
    val vendor = vendorService.getVendor(id)
    return if (vendor != null) {
      ResponseEntity(vendor, HttpStatus.OK)
    } else {
      ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

  @GetMapping
  @Timed
  fun getAll(pageable: Pageable): ResponseEntity<List<Vendor>> {
    val page = repo.findAll(pageable)
    val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
    return ResponseEntity(page.content, headers, HttpStatus.OK)
  }

  @PostMapping
  @Timed
  fun save(@RequestBody @Valid vendor: Vendor): ResponseEntity<Vendor> {

    if (vendor.getId() != null) {
      val failureHeaders = HeaderUtil.createFailureAlert(entityName,
              "idexists", "A new $entityName cannot already have an ID")
      return ResponseEntity(failureHeaders, HttpStatus.BAD_REQUEST)
    }

    val savedEntity = repo.save(vendor)
    return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(entityName, savedEntity.getId().toString()))
            .body(savedEntity)
  }


  @PutMapping("/{id}")
  @Timed
  fun update(@PathVariable("id") id: Long, @RequestBody @Valid vendor: Vendor): ResponseEntity<Vendor> {

    Assert.isTrue(vendor.getId() == id, "id of vendor to be updated should not be null")

    val updatedEntity = repo.save(vendor)
    return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(entityName, vendor.getId().toString()))
            .body(updatedEntity)
  }

  @PutMapping
  @Timed
  fun updateEntity(@RequestBody @Valid vendor: Vendor): ResponseEntity<Vendor> {
    Assert.isTrue(vendor.getId() != null, "id of vendor to be updated should not be null")
    return update(vendor.getId()!!, vendor)
  }


  @DeleteMapping("/{id}")
  @Timed
  fun delete(@PathVariable("id") id: Long): ResponseEntity<Void> {
    repo.delete(id)
    return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityDeletionAlert(entityName, id.toString()))
            .build()
  }

  companion object VendorApi {
    const val baseUrl = "/api/vendors"
    const val entityName = "vendor"
  }
}

