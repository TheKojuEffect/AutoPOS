package com.kapilkoju.autopos.kernel.api

import com.codahale.metrics.annotation.Timed
import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

interface GetApi<T : AuditableEntity> : Api<T> {

    @GetMapping(value = ["/{id}"])
    @Timed
    fun get(@PathVariable("id") entityOp: T?): ResponseEntity<T> {

        return if (entityOp != null) {
            ResponseEntity(entityOp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
