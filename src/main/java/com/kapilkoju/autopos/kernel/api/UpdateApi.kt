package com.kapilkoju.autopos.kernel.api


import com.kapilkoju.autopos.kernel.domain.AuditableEntity
import com.kapilkoju.autopos.web.rest.util.HeaderUtil
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

interface UpdateApi<T : AuditableEntity> : Api<T> {

    @PutMapping(value = ["/{id}"])

    fun update(@PathVariable("id") id: Long, @RequestBody @Valid entity: T): ResponseEntity<T> {

        Assert.isTrue(entity.getId() == id, "id of entity to be updated should not be null")

        val updatedEntity = repo.save(entity)
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(entityName, entity.getId().toString()))
                .body(updatedEntity)
    }

    @PutMapping

    fun updateEntity(@RequestBody @Valid entity: T): ResponseEntity<T> {
        Assert.isTrue(entity.getId() != null, "id of entity to be updated should not be null")
        return update(entity.getId()!!, entity)
    }
}
