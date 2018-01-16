package com.kapilkoju.autopos.kernel.api


import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

interface CreateApi<T : AuditableEntity> : Api<T> {

    @PostMapping

    fun save(@RequestBody @Valid entity: T): ResponseEntity<T> {

        if (entity.getId() != null) {
            val failureHeaders = HeaderUtil.createFailureAlert(entityName,
                    "idexists", "A new $entityName cannot already have an ID")
            return ResponseEntity(failureHeaders, HttpStatus.BAD_REQUEST)
        }

        val savedEntity = repo.save(entity)
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityCreationAlert(entityName, savedEntity.getId().toString()))
                .body(savedEntity)
    }
}
