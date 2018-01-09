package com.kapilkoju.autopos.party.api

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.party.domain.Customer
import com.kapilkoju.autopos.party.service.CustomerRepo
import com.kapilkoju.autopos.party.service.CustomerService
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(CustomerApi.baseUrl)
class CustomerApi(private val repo: CustomerRepo, private val customerService: CustomerService) {


    @GetMapping("{id}")
    @Timed
    fun get(@PathVariable("id") id: Long): ResponseEntity<Customer> {
        val customer = customerService.getCustomer(id)
        return if (customer != null) {
            ResponseEntity(customer, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    @Timed
    fun getAll(pageable: Pageable): ResponseEntity<List<Customer>> {
        val page = repo.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    @PostMapping
    @Timed
    fun save(@RequestBody @Valid customer: Customer): ResponseEntity<Customer> {

        if (customer.getId() != null) {
            val failureHeaders = HeaderUtil.createFailureAlert(entityName,
                    "idexists", "A new $entityName cannot already have an ID")
            return ResponseEntity(failureHeaders, HttpStatus.BAD_REQUEST)
        }

        val savedEntity = repo.save(customer)
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityCreationAlert(entityName, savedEntity.getId().toString()))
                .body(savedEntity)
    }


    @PutMapping("/{id}")
    @Timed
    fun update(@PathVariable("id") id: Long, @RequestBody @Valid customer: Customer): ResponseEntity<Customer> {

        Assert.isTrue(customer.getId() == id, "id of customer to be updated should not be null")

        val updatedEntity = repo.save(customer)
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(entityName, customer.getId().toString()))
                .body(updatedEntity)
    }

    @PutMapping
    @Timed
    fun updateEntity(@RequestBody @Valid customer: Customer): ResponseEntity<Customer> {
        Assert.isTrue(customer.getId() != null, "id of customer to be updated should not be null")
        return update(customer.getId()!!, customer)
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

    companion object CustomerApi {
        const val baseUrl = "/api/customers"
        const val entityName = "customer"
    }
}

