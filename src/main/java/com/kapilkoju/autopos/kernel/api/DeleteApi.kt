package com.kapilkoju.autopos.kernel.api


import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable


interface DeleteApi<T : AuditableEntity> : Api<T> {

    @DeleteMapping(value = ["/{id}"])

    fun delete(@PathVariable("id") id: Long): ResponseEntity<Void> {
        repo.delete(id)
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityDeletionAlert(entityName, id.toString()))
                .build<Void>()
    }
}
