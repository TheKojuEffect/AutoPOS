package com.kapilkoju.autopos.kernel.api

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.web.rest.util.PaginationUtil
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

interface GetAllApi<T : AuditableEntity> : Api<T> {

    @GetMapping
    @Timed
    fun getAll(pageable: Pageable): ResponseEntity<List<T>> {
        val page = repo.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl)
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }
}
